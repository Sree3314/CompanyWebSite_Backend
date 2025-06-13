package com.example.MainProject.config;
////
////import com.example.MainProject.filter.JwtRequestFilter;
////
////import com.example.MainProject.service.CustomUserDetailsService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.http.HttpMethod;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.CorsConfigurationSource;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////import java.util.Arrays;
////import java.util.List;
////
/////**
//// * Spring Security Configuration for the unified MainProject application.
//// *
//// * THIS VERSION IS FOR PHASE 3: EXHIBITION MODULE TESTING.
//// * - Authentication and user profile endpoints remain accessible.
//// * - Upload creation and retrieval endpoints remain accessible ONLY to authenticated users.
//// * - Public exhibition viewing (`/api/exhibition`) is now permitted for all.
//// * - Manager-only actions (rating/commenting uploads) require MANAGER role.
//// */
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    private final CustomUserDetailsService userDetailsService;
////    private final JwtRequestFilter jwtRequestFilter;
////
////    @Autowired
////    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
////        this.userDetailsService = userDetailsService;
////        this.jwtRequestFilter = jwtRequestFilter;
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        return authenticationConfiguration.getAuthenticationManager();
////    }
////
////    @Bean
////    public DaoAuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
////        authProvider.setUserDetailsService(userDetailsService);
////        authProvider.setPasswordEncoder(passwordEncoder());
////        return authProvider;
////    }
////
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Allow Angular app origin
////        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
////        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
////        configuration.setAllowCredentials(true);
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration); // Apply to all paths
////        return source;
////    }
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////            .csrf(AbstractHttpConfigurer::disable)
////            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
////            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////            .authorizeHttpRequests(authorize -> authorize
////                // Publicly accessible authentication endpoints
////                .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
////
////                // Authenticated access for user profile endpoints
////                .requestMatchers("/api/users/me").authenticated()
////
////                // Authenticated access for ALL user upload endpoints (POST, GET by ID, GET my-uploads)
////                .requestMatchers("/api/uploads", "/api/uploads/*", "/api/uploads/my-uploads").authenticated()
////                // Specific access for manager actions on uploads (rating/commenting)
////                .requestMatchers(HttpMethod.PATCH, "/api/uploads/*/rate", "/api/uploads/*/comment").hasRole("MANAGER")
////
////
////                // Publicly accessible exhibition viewing
////                .requestMatchers(HttpMethod.GET, "/api/exhibition").permitAll()
////
////                // DENY any other request by default
////                .anyRequest().denyAll()
////            );
////
////        http.authenticationProvider(authenticationProvider());
////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
////}
//
//package com.example.MainProject.config;
// 
//import com.example.MainProject.filter.JwtRequestFilter;
//import com.example.MainProject.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import java.util.Arrays;
//import java.util.List;
// 
///**
//* Spring Security Configuration for the unified MainProject application.
//*
//* THIS VERSION IS FOR PHASE 3: EXHIBITION MODULE TESTING.
//* - Authentication and user profile endpoints remain accessible.
//* - Upload creation and retrieval endpoints remain accessible ONLY to authenticated users.
//* - Public exhibition viewing (`/api/exhibition`) is now permitted for all.
//* - Manager-only actions (rating/commenting uploads) require MANAGER role.
//*/
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
// 
//    private final CustomUserDetailsService userDetailsService;
//    private final JwtRequestFilter jwtRequestFilter;
// 
//    @Autowired
//    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
//        this.userDetailsService = userDetailsService;
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
// 
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
// 
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
// 
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
// 
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Allow Angular app origin
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // Apply to all paths
//        return source;
//    }
// 
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(AbstractHttpConfigurer::disable)
//            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(authorize -> authorize
//                // Publicly accessible authentication endpoints
//                .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
// 
//                // Authenticated access for user profile endpoints
//                .requestMatchers("/api/users/me").authenticated()
// 
//                // Authenticated access for ALL user upload endpoints (POST, GET by ID, GET my-uploads)
//                .requestMatchers("/api/uploads", "/api/uploads/*", "/api/uploads/my-uploads").authenticated()
//                // Specific access for manager actions on uploads (rating/commenting)
//                .requestMatchers(HttpMethod.PATCH, "/api/uploads/*/rate", "/api/uploads/*/comment").hasRole("MANAGER")
//                
//             // Authenticated access for FAQ module
//                .requestMatchers("/api/faq/**").authenticated()
// 
//                // Publicly accessible exhibition viewing
//                .requestMatchers(HttpMethod.GET, "/api/exhibition").permitAll()
//             // Job-related end points
//                .requestMatchers(HttpMethod.POST, "/api/jobs").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.GET, "/api/jobs/manager/**").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.GET, "/api/jobs").hasAnyRole("MANAGER", "USER")
// 
//                // Application-related end ssspoints
//                .requestMatchers(HttpMethod.POST, "/api/applications").hasRole("USER")
//                .requestMatchers(HttpMethod.GET, "/api/applications/jobs/**").hasRole("MANAGER")
//                .requestMatchers(HttpMethod.GET, "/api/applications/employee/**").hasRole("USER")
//                .requestMatchers(HttpMethod.PUT, "/api/applications/*/status").hasRole("MANAGER")
// 
// 
//             
//                // DENY any other request by default
//                .anyRequest().denyAll()
//            );
// 
//        http.authenticationProvider(authenticationProvider());
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
// 
//        return http.build();
//    }
//}
// package com.example.MainProject.config;
 
import com.example.MainProject.filter.JwtRequestFilter;
import com.example.MainProject.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // CORRECT IMPORT
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;
 
/**
 * Spring Security Configuration for the unified MainProject application.
 *
 * THIS VERSION INCLUDES ALL RULES FOR AUTH, UPLOADS, EXHIBITION, AND EMAIL PASSWORD RESET.
 * CORRECTED: `addFilterBefore` usage.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
 
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
 
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
 
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Allow Angular app origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all paths
        return source;
    }
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
            		 // Publicly accessible authentication endpoints
                  .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
   
                  // Authenticated access for user profile endpoints
                  .requestMatchers("/api/users/me").authenticated()
   
                  // Authenticated access for ALL user upload endpoints (POST, GET by ID, GET my-uploads)
                  .requestMatchers("/api/uploads", "/api/uploads/*", "/api/uploads/my-uploads").authenticated()
                  // Specific access for manager actions on uploads (rating/commenting)
                  .requestMatchers(HttpMethod.PATCH, "/api/uploads/*/rate", "/api/uploads/*/comment").hasRole("MANAGER")
                  .requestMatchers("/ws/**").permitAll() 
                  
               // Authenticated access for FAQ module
                  .requestMatchers("/api/faq/**").authenticated()
   
                  // Publicly accessible exhibition viewing
                  .requestMatchers(HttpMethod.GET, "/api/exhibition").authenticated()
               // Job-related end points
                  .requestMatchers(HttpMethod.POST, "/api/jobs").hasRole("MANAGER")
                  .requestMatchers(HttpMethod.GET, "/api/jobs/manager/**").hasRole("MANAGER")
                  .requestMatchers(HttpMethod.GET, "/api/jobs").hasAnyRole("MANAGER", "USER")
   
                  // Application-related end ssspoints
                  .requestMatchers(HttpMethod.POST, "/api/applications").hasRole("USER")
                  .requestMatchers(HttpMethod.GET, "/api/applications/jobs/**").hasRole("MANAGER")
                  .requestMatchers(HttpMethod.GET, "/api/applications/employee/**").hasRole("USER")
                  .requestMatchers(HttpMethod.PUT, "/api/applications/*/status").hasRole("MANAGER")
   
   
               
                  // DENY any other request by default
                  .anyRequest().denyAll()
              );
 
        http.authenticationProvider(authenticationProvider());
        // CORRECTED LINE: jwtRequestFilter runs before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
}
 
 

