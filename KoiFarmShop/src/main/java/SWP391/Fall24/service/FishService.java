package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.repository.IFishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FishService implements IFishService {
    @Autowired
    private IFishRepository fishRepository;

    @Override
    public List<Fishes> findAll() {
        return fishRepository.findAll();
    }

    @Override
    public Optional<Fishes> findById(int id) {
        return fishRepository.findById(id);
    }
}
