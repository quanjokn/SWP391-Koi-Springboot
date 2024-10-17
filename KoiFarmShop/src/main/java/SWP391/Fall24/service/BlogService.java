package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Blogs;
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
        Blogs newBlog = iBlogRepository.save(blog);
        return newBlog;
    }

    @Override
    public Blogs updateBlog(Blogs blog) {
        return iBlogRepository.save(blog);
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
