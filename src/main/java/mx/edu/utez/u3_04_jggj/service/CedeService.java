package mx.edu.utez.u3_04_jggj.service;

import mx.edu.utez.u3_04_jggj.repository.CedeRepository;
import org.springframework.stereotype.Service;

@Service
public class CedeService {
    private final CedeRepository cedeRepository;

    public CedeService(CedeRepository cedeRepository) {
        this.cedeRepository = cedeRepository;
    }



}
