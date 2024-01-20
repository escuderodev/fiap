package br.com.escuderodev.parking.models.driver;

public record DriverListData(
        Long id,
        String name,
        String phone,
        String address
) {
    public DriverListData(Driver driver) {
        this(driver.getId(), driver.getName(), driver.getPhone(), driver.getAddress());
    }
}
