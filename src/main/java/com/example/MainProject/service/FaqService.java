// src/main/java/com/example/MainProject/service/FaqService.java (Updated - as provided previously)
package com.example.MainProject.service;
 
import com.example.MainProject.dto.AnswerRequest;
import com.example.MainProject.dto.AnswerResponse;
import com.example.MainProject.dto.QuestionRequest;
import com.example.MainProject.dto.QuestionResponse;
import com.example.MainProject.model.Answer;
import com.example.MainProject.model.Question;
import com.example.MainProject.model.User;
import com.example.MainProject.repository.AnswerRepository;
import com.example.MainProject.repository.QuestionRepository;
import com.example.MainProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate; // Import SimpMessagingTemplate
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@Service
public class FaqService {
 
	private static final Logger logger = LoggerFactory.getLogger(FaqService.class);
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate; // Inject SimpMessagingTemplate
 
    @Autowired
    public FaqService(QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate; // Initialize it
    }
 
    // --- Question Methods ---
 
    @Transactional
    public QuestionResponse postQuestion(QuestionRequest questionRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
 
        Question question = new Question();
        question.setTitle(questionRequest.getTitle());
        question.setContent(questionRequest.getContent());
        question.setUser(user);
        question.setPostedAt(LocalDateTime.now());
        question.setLastUpdatedAt(LocalDateTime.now());
 
        Question savedQuestion = questionRepository.save(question);
        return convertToQuestionResponse(savedQuestion);
    }
 
    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::convertToQuestionResponse)
                .collect(Collectors.toList());
    }
 
    @Transactional(readOnly = true)
    public QuestionResponse getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
        return convertToQuestionResponse(question);
    }
    @Transactional
    public QuestionResponse updateQuestion(Long questionId, QuestionRequest questionRequest, Long userId) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
 
        existingQuestion.setTitle(questionRequest.getTitle());
        existingQuestion.setContent(questionRequest.getContent());
        existingQuestion.setLastUpdatedAt(LocalDateTime.now());
 
        Question updatedQuestion = questionRepository.save(existingQuestion);
        return convertToQuestionResponse(updatedQuestion);
    }
    @Transactional
    public void deleteQuestion(Long questionId, Long userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
 
        questionRepository.delete(question);
    }
 
    // --- Answer Methods ---
 
    @Transactional
    public AnswerResponse postAnswer(Long questionId, AnswerRequest answerRequest, Long userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
 
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
 
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setContent(answerRequest.getContent());
        answer.setPostedAt(LocalDateTime.now());
        answer.setLastUpdatedAt(LocalDateTime.now());
 
        Answer savedAnswer = answerRepository.save(answer);
 
        // Publish the new answer to WebSocket clients
        AnswerResponse response = convertToAnswerResponse(savedAnswer);
        String destination = "/topic/questions/" + questionId + "/answers";
 
        logger.info("Attempting to send WebSocket message to destination: {} with payload: {}", destination, response);
 
        messagingTemplate.convertAndSend(destination, response); // Sends the AnswerResponse object
 
        return response;
    }
 
    @Transactional
    public AnswerResponse updateAnswer(Long answerId, AnswerRequest answerRequest, Long userId) {
        Answer existingAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found with ID: " + answerId));
 
        existingAnswer.setContent(answerRequest.getContent());
        existingAnswer.setLastUpdatedAt(LocalDateTime.now());
 
        Answer updatedAnswer = answerRepository.save(existingAnswer);
        return convertToAnswerResponse(updatedAnswer);
    }
 
    @Transactional
    public void deleteAnswer(Long answerId, Long userId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found with ID: " + answerId));
 
        answerRepository.delete(answer);
    }
 
    // --- Conversion Methods ---
 
    private QuestionResponse convertToQuestionResponse(Question question) {
        String userName = (question.getUser().getFirstName() != null && question.getUser().getLastName() != null) ?
                question.getUser().getFirstName() + " " + question.getUser().getLastName() :
                question.getUser().getEmail();
        String userRole = question.getUser().getRole() != null ? question.getUser().getRole().name() : null;
 
        List<AnswerResponse> answerResponses = question.getAnswers().stream()
                .map(this::convertToAnswerResponse)
                .collect(Collectors.toList());
 
        return new QuestionResponse(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getUser().getEmployeeId(),
                userName,
                userRole,
                question.getUser().getProfilePictureUrl(),
                question.getPostedAt(),
                question.getLastUpdatedAt(),
                answerResponses
        );
    }
 
    private AnswerResponse convertToAnswerResponse(Answer answer) {
        String userName = (answer.getUser().getFirstName() != null && answer.getUser().getLastName() != null) ?
                answer.getUser().getFirstName() + " " + answer.getUser().getLastName() :
                answer.getUser().getEmail();
        String userRole = answer.getUser().getRole() != null ? answer.getUser().getRole().name() : null;
 
        return new AnswerResponse(
                answer.getId(),
                answer.getQuestion().getId(),
                answer.getContent(),
                answer.getUser().getEmployeeId(),
                userName,
                userRole,
                answer.getUser().getProfilePictureUrl(),
                answer.getPostedAt(),
                answer.getLastUpdatedAt()
        );
    }
}
 