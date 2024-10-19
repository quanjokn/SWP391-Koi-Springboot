package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Blogs;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BlogService implements IBlogService{
    @Autowired
    IBlogRepository iBlogRepository;

    @Override
    public Blogs findById(int postId) {
        return iBlogRepository.findById(postId);
    }

    @Override
    public Blogs createBlog(Blogs blog) {
        List<Blogs> list = iBlogRepository.findAll();
        int i = list.size();
        blog.setId(++i);
        iBlogRepository.save(blog);
        return blog;
    }

    @Override
    public Blogs updateBlog(Blogs blog) {
        return iBlogRepository.save(blog);
    }

    @Override
    public String deleteBlog(int blogID) {
        Blogs blog = iBlogRepository.findById(blogID);
        blog.setStaff(new Users());
        iBlogRepository.save(blog);
        iBlogRepository.delete(blog);
        return "Delete completely";
    }

    @Override
    public List<Blogs> searchBlog(String title) {
        return iBlogRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Blogs> getAll() {
        return iBlogRepository.findAll();
    }

}
