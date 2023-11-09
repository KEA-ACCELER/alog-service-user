 # 🌼 동시편집 기능을 제공하는 릴리즈 노트 공유 시스템, A-LOG

**개발 기간** 2023.06 ~ 2023.08 <br/>
**사이트 바로가기** https://alog.acceler.kr/ (🔧업데이트 중) <br/>
**Team repo** https://github.com/orgs/KEA-ACCELER/repositories <br/>

# 🐳 Overview Architecture

![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/b9f31a1a-6375-4f6e-af24-02d4b308002a)

Here, the domains(service)'s relationship is shown. <br/>
![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/8d984dee-63b2-4345-b78e-040f9f7279fa)

# 📚  Implementation

User service handling the information of users.
User service aims to manage user information, team, and team member information. Users manage profile information and efficiently support collaboration through functions such as team creation, team member management, and e-mail authentication.

## Service Flow and Features 

### **1. User sign up and email authentication**

- Users move to the sign-up page and enter the required information.
- The system creates an authentication code and sends an email to the entered email.
- The email contains an authentication code.

### **2. Email authentication confirmation**

- Users check the email and click the authentication link or enter the authentication code.
- The system checks the email authentication and changes the user's authentication status.

### **3. User sign up complete**

- When the email authentication is complete, the user completes the sign-up process.
- Users can log in with a new account by moving to the login page.

### **4. Profile information management**

- Users move to the profile page after logging in to manage personal information.
- You can change your profile picture, change your password, and more.

### **5. Team creation and join**

- Users can move to the team creation page and create a new team.
- Someone who make the team can manage the team.
- You can check the existing team list and request to join.

### **6. Team management**

- Team managers can add or remove team members.
- The team member list is dynamically updated to reflect changes in team members.

## Interface

> [check swagger section below](#🍀-swagger)

These are for user and team management, and for the system's api interface for sign up and email authentication. To describe the meaning and function of some api interfaces:

- GET `/api/users/info`: This is an api interface that retrieves user information. You must pass the user's id as a parameter.
- DELETE `/api/users/delete`: This is an api interface that deletes a user's account. You must pass the user's id as a parameter.
- GET `/api/users/teams`: This is an api interface that retrieves a list of teams to which a user belongs. You must pass the user's id as a parameter.
- DELETE `/api/users/teams`: This is an api interface that deletes a team to which a user belongs. You must pass the user's id and the team's id as a parameter.
- POST `/api/users/teams-members`: This is an api interface that allows a user to join a new team. You must pass the user's id, the team's id, and the role as parameters.
- POST `/api/emails/verify`: This is an api interface that handles a user's email authentication. You must pass the user's email and code as parameters.
- POST `/api/emails/send`: This is an api interface that sends an email code to a user. You must pass the user's email as a parameter.

## Dependencies
> ./gradlew dependencies
```
implementation - Implementation only dependencies for source set 'main'. (n)
+--- org.springframework.boot:spring-boot-starter (n)
+--- org.springframework.boot:spring-boot-starter-data-jpa (n)
+--- org.springframework.boot:spring-boot-starter-web (n)
+--- javax.xml.bind:jaxb-api:2.3.0 (n)
+--- org.springframework.cloud:spring-cloud-starter-openfeign:4.0.2 (n)
+--- org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2 (n)
+--- org.springframework.boot:spring-boot-starter-validation (n)
\--- org.springframework.boot:spring-boot-starter-mail (n)
```

## ERD
![image (3)](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/a1e575c4-df8a-4748-b852-869666b94068)



# ✨ Installation

## Running the user app only 

- use docker-compose.yml
```
docker compose up -d
```


# 🍀 Swagger
```
http://localhost:30000/api/users
```
![image](https://github.com/KEA-ACCELER/alog-service-project/assets/80394866/a59bd8e3-ab90-447a-b695-213077db87ba)

# 📝 Conclusion and Suggestion

## **If try to improve the quality**

- Database performance optimization: Optimize performance for large data processing to improve response speed.
- Improve user experience: Improve user profile management and team joining procedures to be more user-friendly.

## **If try to improve the function**

- Strengthen team management functions: Add functions such as project management, schedule management, and collaboration tool integration to support team collaboration more effectively.
- Expand email service: Add functions such as notification and notification subscription to enhance communication between users and teams.

## **Conclusion**

The **`User`** service plays an important role in supporting efficient collaboration and team management by managing user information and team and team member information. Continuous quality improvement and feature enhancement will provide users with a better experience.
