package com.hyl.services.nacosuser.demo;

import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@RestController
public class UserController {
    private final PersonRepository personRepository;

    public UserController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/get")
    public Object getUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Iterable<Person>  persons =  personRepository.findAll();
        for (Person person:persons) {
            System.out.println(person.toString());
            convert(person.getPassword());
        }
        return persons;
    }

    private void convert(byte[] password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(new String(password));
        String liudehuamd5 = convertMD5("liudehua", "md5");
        System.out.println("liudehua: " + liudehuamd5 + " isTrue:" + Objects.equals(liudehuamd5, new String(password)));
    }


    private String convertMD5(String msg, String encodeType) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(encodeType);
        return "{md5}".toUpperCase().concat(Base64Utils.encodeToString(md5.digest(msg.getBytes())));
    }

    @GetMapping("/save")
    public void save() throws NoSuchAlgorithmException {
        Person person = new Person();
        person.setId(LdapNameBuilder.newInstance().add("cn=fucheng guo,ou=hr,cn=bj").build());
        person.setUid("guofucheng");
        person.setCn("fucheng guo");
        person.setGidnumber("502");
        person.setSn("guo");
        person.setPassword(convertMD5("guofucheng", "md5").getBytes());
        person = personRepository.save(person);
        System.out.println(person);
    }
}
