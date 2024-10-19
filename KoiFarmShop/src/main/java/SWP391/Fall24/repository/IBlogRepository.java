package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IBlogRepository extends JpaRepository<Blogs, Integer> {
    List<Blogs> findByTitleContainingIgnoreCase(String title);
    Blogs findById(int postId);
    Blogs save(Blogs blogs);
}
