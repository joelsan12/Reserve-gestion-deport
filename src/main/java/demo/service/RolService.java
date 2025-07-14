package demo.service;

import demo.model.Rol;
import demo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @PostConstruct
    public void initRoles() {
        if (rolRepository.findByNombre("ADMIN") == null) {
            rolRepository.save(new Rol(null, "ADMIN"));
        }
        if (rolRepository.findByNombre("USER") == null) {
            rolRepository.save(new Rol(null, "USER"));
        }
    }
}
