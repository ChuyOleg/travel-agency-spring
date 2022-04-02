package com.oleh.chui.model.service.util.pagination;

import com.oleh.chui.model.entity.Tour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginationInfo {

    private List<Tour> tourList;
    private int pagesNumber;

}
