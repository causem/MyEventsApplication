package com.example.MyEvents.controller.view;

import com.example.MyEvents.dto.*;
import com.example.MyEvents.exception.AlreadyRegisteredException;
import com.example.MyEvents.exception.EventNotFoundException;
import com.example.MyEvents.exception.ParticipantNotFoundException;
import com.example.MyEvents.exception.RegistrationFullException;
import com.example.MyEvents.service.EventService;
import com.example.MyEvents.service.LocationService;
import com.example.MyEvents.service.ParticipantService;
import com.example.MyEvents.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventViewController {

    private final EventService eventService;
    private final LocationService locationService;
    private final RegistrationService registrationService;
    private final ParticipantService participantService;

    @GetMapping
    public String list(Model model) {
        List<EventResponseDto> events = eventService.getAll();
        model.addAttribute("events", events);
        return "events/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("eventForm", new EventFormDto());
        model.addAttribute("locations", locationService.getAll());
        return "events/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("eventForm") EventFormDto form,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        boolean hasId = form.getLocationId() != null;
        boolean hasObj = form.getLocation() != null
                && form.getLocation().getName() != null && !form.getLocation().getName().isBlank()
                && form.getLocation().getCity() != null && !form.getLocation().getCity().isBlank()
                && form.getLocation().getAddress() != null && !form.getLocation().getAddress().isBlank();

        if (hasId && hasObj) {
            br.rejectValue("locationId", "oneOf", "Pick existing location OR provide a new one — not both.");
        } else if (!hasId && !hasObj) {
            br.rejectValue("locationId", "oneOf", "Pick existing location OR provide a new one.");
        }

        if (br.hasErrors()) {
            model.addAttribute("locations", locationService.getAll());
            return "events/new";
        }

        LocationDto locDto = hasObj
                ? new LocationDto(
                form.getLocation().getName(),
                form.getLocation().getCity(),
                form.getLocation().getAddress()
        )
                : null;

        EventCreateDto createDto = new EventCreateDto(
                form.getName(),
                form.getDescription(),
                form.getDate(),
                form.getCapacity(),
                hasId ? form.getLocationId() : null,
                locDto
        );

        EventResponseDto created = eventService.create(createDto);
        ra.addFlashAttribute("msg", "Event created successfully");
        return "redirect:/events/" + created.id();
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        EventResponseDto event = eventService.getById(id);
        int available = eventService.availableSeats(id);
        model.addAttribute("event", event);
        model.addAttribute("availableSeats", available);
        return "events/detail";
    }

    @GetMapping("/{id}/registrations")
    public String registrations(@PathVariable Long id, Model model) {
        EventResponseDto event = eventService.getById(id);
        List<RegistrationResponseDto> list = registrationService.getRegistrationsForEvent(id);
        model.addAttribute("event", event);
        model.addAttribute("registrations", list);
        return "registrations/list";
    }

    @PostMapping("/{id}/register")
    public String registerByEmail(@PathVariable Long id,
                                  @RequestParam String email,
                                  RedirectAttributes ra) {
        try {
            ParticipantResponseDto participant = participantService.getByEmail(email);
            registrationService.register(id, participant.id());
            ra.addFlashAttribute("msg", "Successfully registered by email: " + email);
        } catch (ParticipantNotFoundException e) {
            ra.addFlashAttribute("err", "No participant found for email: " + email);
        } catch (AlreadyRegisteredException e) {
            ra.addFlashAttribute("err", "You are already registered for this event.");
        } catch (RegistrationFullException e) {
            ra.addFlashAttribute("err", "Registration is full for this event.");
        }
        return "redirect:/events/" + id;
    }

    /**
     * Utworzenie nowego uczestnika + jednoczesna rejestracja na event.
     */
    @PostMapping("/{id}/registerNew")
    public String registerNew(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String email,
                              RedirectAttributes ra) {
        try {
            ParticipantResponseDto participant;
            try {
                participant = participantService.getByEmail(email);
            } catch (ParticipantNotFoundException e) {
                participant = participantService.create(new ParticipantDto(name, email));
            }

            registrationService.register(id, participant.id());
            ra.addFlashAttribute("msgNew", "Account created/loaded and registered successfully!");
        } catch (AlreadyRegisteredException e) {
            ra.addFlashAttribute("err", "This participant is already registered for the event.");
        } catch (RegistrationFullException e) {
            ra.addFlashAttribute("err", "Registration is full for this event.");
        }
        return "redirect:/events/" + id;
    }

}
