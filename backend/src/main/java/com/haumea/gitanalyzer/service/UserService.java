package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.UserRepository;
import com.haumea.gitanalyzer.model.Configuration;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final String SFU_API_URL = "https://cas.sfu.ca/cas/serviceValidate?service=";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {

        return userRepository.saveUser(user);
    }

    public User updateUser(User user) {

        return userRepository.updateUser(user);
    }

    public String getPersonalAccessToken(String userId) {

        return userRepository.getPersonalAccessToken(userId);
    }

    public String getUserId(String url, String ticket) {
        String finalURL = SFU_API_URL + url + "&ticket=" + ticket;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDocument = builder.parse(new URL(finalURL).openStream());

            xmlDocument.getDocumentElement().normalize();
            NodeList users = xmlDocument.getDocumentElement().getElementsByTagName("cas:user");
            int numberOfUsers = users.getLength();
            if (numberOfUsers == 1) {
                Node firstUser = users.item(0);
                return firstUser.getTextContent();
            }
            return "";
        }
        catch (Exception e)  {
            return "";
        }
    }

    public User saveConfiguration(String userId, Configuration configuration) {
        return userRepository.saveConfiguration(userId, configuration);
    }

    public List<Configuration> getConfigurations(String userId){
        return userRepository.getConfigurations(userId);
    }

    public User updateConfiguration(String userId, Configuration configuration) {
        return userRepository.updateConfiguration(userId, configuration);
    }
}
