package bozlak.java2021.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequestWithoutPicture {
    private String categoryName;
    private String description;
}
