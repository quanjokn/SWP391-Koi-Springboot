package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Fishes;

import java.util.List;
import java.util.Optional;

public interface IFishService  {
    public List<Fishes> findAll();

    public Optional<Fishes> findById(int id);

}
