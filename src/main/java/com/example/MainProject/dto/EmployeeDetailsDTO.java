package com.example.MainProject.dto;

public class EmployeeDetailsDTO {
  
	
		private Long employeeId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    
	    
	    
	    public EmployeeDetailsDTO(Long employeeId, String firstName, String lastName, String email) {
	
			this.employeeId = employeeId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}
		public Long getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
}
