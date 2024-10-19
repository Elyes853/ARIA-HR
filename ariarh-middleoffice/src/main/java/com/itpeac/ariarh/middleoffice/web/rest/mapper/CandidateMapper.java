package com.itpeac.ariarh.middleoffice.web.rest.mapper;
import com.itpeac.ariarh.middleoffice.domain.Candidate;
import com.itpeac.ariarh.middleoffice.service.dto.CandidateDTO;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper implements Mapper<Candidate, CandidateDTO>{
    @Override
    public CandidateDTO fromEntityToDto(Candidate candidate) {
        if (candidate != null) {
            CandidateDTO candidateDTO = new CandidateDTO();
            candidateDTO.setId(candidate.getId());
            candidateDTO.setEmail(candidate.getEmail());
            candidateDTO.setName(candidate.getName());
            candidateDTO.setLocation(candidate.getLocation());
            candidateDTO.setRole(candidate.getRole());
            candidateDTO.setPhone(candidate.getPhone());
            candidateDTO.setCv(candidate.getCv());
           

            // Set other attributes here

            return candidateDTO;
        }
        return null;
    }

    @Override
    public Candidate fromDTOtoEntity(CandidateDTO candidateDTO) {
        if (candidateDTO != null) {
            Candidate candidate = new Candidate();
            candidate.setId(candidateDTO.getId());
            candidate.setRole(candidateDTO.getRole());
            candidate.setPhone(candidateDTO.getPhone());
            candidate.setEmail(candidateDTO.getEmail());
            candidate.setLocation(candidateDTO.getLocation());
            candidate.setName(candidateDTO.getName());
            candidate.setCv(candidateDTO.getCv());

            // Set other attributes here

            return candidate;
        }
        return null;
    }
}
