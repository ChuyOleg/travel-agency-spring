package com.oleh.chui.controller.common;

import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TourDetailsController {

    private final TourService tourService;

    @GetMapping(UriPath.TOUR_DETAILS + "/{id}")
    public void getTourDetailsPage(@PathVariable Long id, Model model) {

    }

}
