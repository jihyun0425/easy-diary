package com.example.easydiary.controller;

import com.example.easydiary.entity.Post;
import com.example.easydiary.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/private/diary")
    public String listPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "diary"; // 게시글 목록 페이지 템플릿 이름
    }


    @GetMapping("/private/new")
    public String createPostForm(@RequestParam(required = false) String error, Model model) {
        if ("title".equals(error)) {
            model.addAttribute("errorMessage", "제목을 작성해주세요.");
        }
        return "write"; // 글 작성 페이지 템플릿 이름
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p -> model.addAttribute("post", p));
        return "view"; // 상세 보기 페이지 템플릿 이름
    }

    @GetMapping("/private/cuteWrite")
    public String cuteWritePost(@RequestParam(required = false) String error, Model model) {
        if ("title".equals(error)) {
            model.addAttribute("errorMessage", "제목을 작성해주세요.");
        }
        return "cuteWrite"; // 귀여운 글 작성 페이지 템플릿 이름
    }

    @GetMapping("/private/emotionalWrite")
    public String emotionalWritePost(@RequestParam(required = false) String error, Model model) {
        if ("title".equals(error)) {
            model.addAttribute("errorMessage", "제목을 작성해주세요.");
        }
        return "emotionalWrite"; // 감성적인 글 작성 페이지 템플릿 이름
    }

    @GetMapping("/private/funnyWrite")
    public String funnyWritePost(@RequestParam(required = false) String error, Model model) {
        if ("title".equals(error)) {
            model.addAttribute("errorMessage", "제목을 작성해주세요.");
        }
        return "funnyWrite"; // 재미있는 글 작성 페이지 템플릿 이름
    }

    @GetMapping("/private/monthCalendar")
    public String monthCalendarPost() {
        return "monthCalendar"; // 월간 캘린더 페이지 템플릿 이름
    }

    @GetMapping("/private/weekCalendar")
    public String weekCalendarPost() {
        return "weekCalendar"; // 주간 캘린더 페이지 템플릿 이름
    }


    @PostMapping("/post/diary")
    public String createPost(@RequestParam String title, @RequestParam String content, @RequestParam String pageType, Model model) {
        // 현재 시간 설정
        LocalDateTime createdAt = LocalDateTime.now();

        // 받아온 데이터로 Post 객체 생성
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(createdAt);

        // 제목이 비어 있는지 확인
        if (title.trim().isEmpty()) {
            // 제목이 비어 있으면 에러 메시지를 모델에 추가하여 다시 작성 페이지로 이동
            model.addAttribute("error", "제목을 작성해주세요.");
            if ("write".equals(pageType)) {
                return "write";
            } else if ("cuteWrite".equals(pageType)) {
                return "cuteWrite";
            } else if ("emotionalWrite".equals(pageType)) {
                return "emotionalWrite";
            } else if ("funnyWrite".equals(pageType)) {
                return "funnyWrite";
            }
        }

            // 데이터베이스에 저장
            postRepository.save(post);

            // 게시글 목록 페이지로 리디렉션
            return "redirect:/private/diary";

    }




    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/private/diary"; // 삭제 후 목록 페이지로 리다이렉트
    }
}
