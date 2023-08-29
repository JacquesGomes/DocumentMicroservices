package org.example.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    public static String buildDocumentExistenceURL(ArrayList<String> docIds) {
        StringBuilder urlBuilder = new StringBuilder("http://localhost:9000" +
                "/documents/exist?");

        if (docIds != null && !docIds.isEmpty()) {
            for (String docId : docIds) {
                urlBuilder.append("&docids=").append(docId);
            }
        }

        return urlBuilder.toString();
    }

}
