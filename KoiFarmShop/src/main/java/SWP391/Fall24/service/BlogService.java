package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Blogs;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.IBlogRepository;
import SWP391.Fall24.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService{
    @Autowired
    IBlogRepository iBlogRepository;
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public Blogs findById(int postId) {
        return iBlogRepository.findById(postId);
    }

    @Override
    public Blogs createBlog(Blogs blog) {
        List<Blogs> list = iBlogRepository.findAll();
        int i = list.size();
        Blogs blogs = new Blogs();
        blog.setId(++i);
        blogs.setTitle(blog.getTitle());
        blogs.setDescription(blog.getDescription());
        blogs.setTitle_1(blog.getTitle_1());
        blogs.setContent_1(blog.getContent_1());
        blogs.setTitle_2(blog.getTitle_2());
        blogs.setContent_2(blog.getContent_2());;
        Users u =iUserRepository.findById(blog.getStaff().getId());
        blogs.setStaff(u);
        blogs.setDate(blog.getDate());
        iBlogRepository.save(blogs);
        return blogs;
    }

    @Override
    public Blogs updateBlog(Blogs blog) {
        Blogs updateBlogs = new Blogs();
        updateBlogs.setId(blog.getId());
        updateBlogs.setTitle(blog.getTitle());
        updateBlogs.setDescription(blog.getDescription());
        updateBlogs.setTitle_1(blog.getTitle_1());
        updateBlogs.setContent_1(blog.getContent_1());
        updateBlogs.setTitle_2(blog.getTitle_2());
        updateBlogs.setContent_2(blog.getContent_2());;
        Users u =iUserRepository.findById(blog.getStaff().getId());
        updateBlogs.setStaff(u);
        updateBlogs.setDate(blog.getDate());
        return iBlogRepository.save(updateBlogs);
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
