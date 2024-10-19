package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Blogs;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBlogService {
    public List<Blogs> searchBlog(String title);
    public List<Blogs> getAll();
    public Blogs findById(int postId);
    public Blogs createBlog(Blogs blog);
    public Blogs updateBlog(Blogs blog);
    public String deleteBlog(int blogID);
}
