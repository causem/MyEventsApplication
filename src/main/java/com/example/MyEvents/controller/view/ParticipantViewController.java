package com.example.MyEvents.controller.view;

import com.example.MyEvents.dto.ParticipantDto;
import com.example.MyEvents.dto.ParticipantResponseDto;
import com.example.MyEvents.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/participants")
public class ParticipantViewController {

    private final ParticipantService participantService;

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("participant", new ParticipantDto(null, null));
        return "participants/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("participant") ParticipantDto dto,
                         BindingResult br,
                         RedirectAttributes ra) {
        if (br.hasErrors()) return "participants/new";
        ParticipantResponseDto created = participantService.create(dto);
        ra.addFlashAttribute("msg", "Participant created: " + created.email());
        return "redirect:/events";
    }
}
