package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.UserRepository;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final String SFU_API_URL = "https://cas.sfu.ca/cas/serviceValidate?service=";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){

        return userRepository.saveUser(user);
    }

    public User updateUser(User user){

        return userRepository.updateUser(user);
    }

    public String getPersonalAccessToken(String userId) {

        return userRepository.getPersonalAccessToken(userId);
    }

    public String getUserName(String url, String ticket) {
        String finalURL = SFU_API_URL + url + "&ticket=" + ticket;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(finalURL).openStream());

            doc.getDocumentElement().normalize();
            int numberOfUsers = doc.getDocumentElement().getElementsByTagName("cas:user").getLength();
            if (numberOfUsers >= 1) {
                return doc.getDocumentElement().getElementsByTagName("cas:user").item(0).getTextContent();
            }
            return "";
        }
        catch (Exception e)  {
            return "";
        }
    }
}
