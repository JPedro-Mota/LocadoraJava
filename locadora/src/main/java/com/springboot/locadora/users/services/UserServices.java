package com.springboot.locadora.users.services;

import com.springboot.locadora.users.DTOs.CreateUserRecordDTO;
import com.springboot.locadora.users.DTOs.UpdateUserRecordDTO;
import com.springboot.locadora.users.entities.PasswordResetRequest;
import com.springboot.locadora.users.entities.PasswordResetToken;
import com.springboot.locadora.users.entities.UserEntity;
import com.springboot.locadora.users.repositories.PasswordResetTokenRepository;
import com.springboot.locadora.users.repositories.UserRepository;
import com.springboot.locadora.users.validations.UserValidation;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    public ResponseEntity<Void> create(@Valid CreateUserRecordDTO data) {

        userValidation.validateName((data));
        userValidation.validateEmail(data);

        String encryptedPassword = passwordEncoder.encode(data.password());
        UserEntity newUser = new UserEntity(data.name(), data.email(), encryptedPassword, data.role());
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public List<UserEntity> findAll(){
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) throw new ModelNotFoundException();
        return users;
    }

    public Optional<UserEntity> findById(int id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<Object> update(int id, @Valid UpdateUserRecordDTO updateUserRecordDTO){
        Optional<UserEntity> response = userRepository.findById(id);
        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserEntity userEntity = response.get();
        BeanUtils.copyProperties(updateUserRecordDTO, userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userEntity));
    }

    public ResponseEntity<Object> delete(int id){
        Optional<UserEntity> response = userRepository.findById(id);
        if(response.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            userRepository.delete(response.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    public String createPasswordResetToken(String email){
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (!userEntityOptional.isPresent()){
            return null;
        }

        UserEntity user = userEntityOptional.get();

        PasswordResetToken existingToken = resetTokenRepository.findByUser(user);
        if(existingToken !=null){
            if(existingToken.isExpired()){
                resetTokenRepository.delete(existingToken);
            } else {
                return existingToken.getToken();
            }
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken newToken = new PasswordResetToken(token, user);
        resetTokenRepository.save(newToken);

        return token;
    }

    public boolean validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = resetTokenRepository.findByToken(token);
        if (resetToken == null) {
            return false;
        }

        if (resetToken.isExpired()) {
            return false;
        }

        return true;
    }


    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = resetTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            return false;
        }

        UserEntity user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetTokenRepository.delete(resetToken);

        return true;
    }


    public String getUserNameByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getName();
        }
        return null;
    }

}