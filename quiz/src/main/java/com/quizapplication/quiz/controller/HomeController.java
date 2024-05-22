package com.quizapplication.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>" +
                "<style>" +
                "body { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f8f9fa; }" +
                ".content { text-align: center; max-width: 600px; }" +
                "h1 { color: #4CAF50; }" +
                "p { font-size: 16px; color: #555; }" +
                "@media (max-width: 768px) {" +
                "h1 { font-size: 24px; }" +
                "p { font-size: 14px; }" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='content'>" +
                "<h1>Welcome to the Quiz Game Application Backend!</h1>" +
                "<p>Embark on a journey of knowledge and fun with our comprehensive quiz platform.<br/>" +
                "Here, you can manage quiz questions, track scores, and much more.<br/>" +
                "Our API is designed to provide a seamless experience for developers and quiz enthusiasts alike.<br/>" +
                "Dive in and explore the endless possibilities of our Quiz Game Application.</p>" +
                "<p>Feel free to explore our endpoints to get started!</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

}
