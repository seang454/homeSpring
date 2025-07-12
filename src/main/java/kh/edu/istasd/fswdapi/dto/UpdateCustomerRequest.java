package kh.edu.istasd.fswdapi.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
