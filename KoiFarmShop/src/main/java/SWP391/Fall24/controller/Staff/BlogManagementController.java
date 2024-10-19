package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.pojo.Blogs;
import SWP391.Fall24.repository.IBlogRepository;
import SWP391.Fall24.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogManagement")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogManagementController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private IBlogRepository iBlogRepository;

    @PostMapping("/create")
    public Blogs createBlog(@RequestBody Blogs blog) { return blogService.createBlog(blog); }

    @DeleteMapping("/delete/{blogID}")
    public String deleteBlog(@PathVariable("blogID") int blogID) {
        return blogService.deleteBlog(blogID);
    }

    @GetMapping("detail/{blogID}")
    public Blogs getPostDetails(@PathVariable("blogID") int blogID) {
        return blogService.findById(blogID);
    }

    @PostMapping("/update")
    public void updateBlog(@RequestBody Blogs blog) {
        blogService.updateBlog(blog);
    }
}
