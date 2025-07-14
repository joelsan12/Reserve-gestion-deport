package demo.service;

import java.util.List;

import demo.model.Usuario;

public interface UserService {
    Usuario findByEmail(String email);
    Usuario registerUser(Usuario usuario);
    List<Usuario> findAll();
    Usuario findById(Long id);
    void save(Usuario usuario);
    void deleteById(Long id);

}
