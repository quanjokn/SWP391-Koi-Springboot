package SWP391.Fall24.controller;

import SWP391.Fall24.pojo.Blogs;
import SWP391.Fall24.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class BlogController {
    @Autowired
    BlogService blogService;
    @GetMapping
    public List<Blogs> getAll(){
        return blogService.getAll();
    }
    @GetMapping("/search")
    public List<Blogs> searchBlogs(@RequestParam String query){
        return blogService.searchBlog(query);
    }
    @GetMapping("/{id}")
    public Blogs getPostDetails(@PathVariable("id") int postId) {
        return blogService.findById(postId);
    }
}
