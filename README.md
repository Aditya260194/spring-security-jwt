# spring-security-jwt
spring boot + spring security + jwt authorization

By default when spring-security is added, it will ask for all request made to get authenticated. Deafult user is user and password is printed in console.

We will override it for our user using MyUserDetailsService which we can source to validate user from user reporsitory like database etc.
SecurityConfig class contains config setup related to the project.Important point in this is configure method which we use to map authentication for particular urls and also adding our filter nefore spring security deault filter.

Steps:
1. /hello/authenticate will ask user for username and password whcih we are providing as Request Body  using AuthenticationRequest class.
2. It will use spring security's default UsernamePasswordAuthenticationToken class to validate user and password from our MyUserDetailsService

JwtUtil
3. After validating user, we are generating token using JwtUtil and sending the token in response so client can pass this token with eery other request.
When token is generated, we are also setting expiry time of the token.


4. When /hello/world is called, we have setup our config SecurityConfig to validate authentication for this request, with this we are passing jwtToken in header as header -[{"key":"Authorization","value":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTYxOTAzMDE3MCwiaWF0IjoxNjIxMTY1MTM3fQ.ngU4JZjHHJl1T0fXBXYXJw1InA6fIeqmcWWau33ibYs","description":""}]

JwtRequestFilter
5. We have created a filter JwtRequestFilter, which is added before default filter of spring security in SecurityConfig. In this filter we are fetching the token from header and extracting username based on token using JwtUtil class.
6. Since this is called before Spring-security's default filter, so authentication method is not set yet, we want authentication to now check for token not for user and password since this step we are only getting token.
7. We validate the token and build the Authentication by fetching userdetails based on token and user and then storing userdetails in UsernamePasswordAuthenticationToken and ultimately in SecurityContextHolder.getContext().setAuthentication.

8. Now request is validate using token, so we will get our output. If we dont probide token then it will authentication error.

Note: In SecurityConfig, we have defined the session to be stateless meaning, it wont store session without authorizing user, so for every request we have to pass jwttoken, as previous token is not stored in sessiom.
