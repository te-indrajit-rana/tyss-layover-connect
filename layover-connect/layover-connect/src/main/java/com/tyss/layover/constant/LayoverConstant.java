package com.tyss.layover.constant;

public class LayoverConstant {

	// UserPojo constant

	public static final String USER_TYPE_CANNOT_BE_NULL = "User Type Cannot Be Null";
	public static final String USER_TYPE_CANNOT_BE_BLANK = "User Type Cannot Be Blank";
	public static final String LOCATION_CANNOT_BE_NULL = "Location Cannot Be Null";
	public static final String LOCATION_CANNOT_BE_BLANK = "Location Cannot Be Blank";
	public static final String COUNTRY_CANNOT_BE_NULL = "Country Cannot Be Null";
	public static final String COUNTRY_CANNOT_BE_BLANK = "Country Cannot Be Blank";
	public static final String INVALID_PHONE_NUMBER = "Invalid Phone Number";
	public static final String INVALID_EMAIL_ADDRESS = "Invalid Email Address";
	public static final String PERSON_NAME_CANNOT_BE_NULL = "Person Name Cannot Be Null";
	public static final String PERSON_NAME_CANNOT_BE_BLANK = "Person Name Cannot Be Blank";
	public static final String USER_TITLE_CANNOT_BE_NULL = "User Title Cannot Be Null";
	public static final String USER_TITLE_CANNOT_BE_BLANK = "User Title Cannot Be Blank";
	public static final String USER_ADDRESS_CANNOT_BE_NULL = "User Address Cannot Be Null";
	public static final String USER_ADDRESS_CANNOT_BE_BLANK = "User Address Cannot Be Blank";
	public static final String USER_NAME_CANNOT_BE_NULL = "User Name Cannot Be Null";
	public static final String USER_NAME_CANNOT_BE_BLANK = "User Name Cannot Be Blank";

	// End of UserPojo Constant

	// Exception constant
	public static final String OTP_IS_NOT_VALID = "Otp Is Not Valid";
	public static final String USER_HAS_BEEN_DELETED = "User Has Been Deleted";
	public static final String ACCOUNT_HAS_BEEN_NOT_APPROVED_BY_ADMIN = "Account Has Been Not Approved By Admin";
	public static final String GIVEN_USER_IS_NOT_PRESENT = "Given User Is Not Present";
	public static final String USER_NOT_FOUND = "User Not Found";
	public static final String ROOM_COUNT_CANNOT_BE_A_NEGATIVE_VALUE = "Room Count Cannot Be a Negative Value";

	public static final String PRICE_CANNOT_BE_A_NEGATIVE_VALUE = "Price Cannot Be A Negative Value";

	public static final String ERROR_WHILE_CONVERTING_MULTIPART_FILE_TO_FILE = "Error while converting multipart file to file.";
	public static final String FAILED_TO_UPLOAD = "Failed to upload.";

	// End of exception

	// Service constant start

	public static final String OTP_EXPIRED = "Otp Expired";
	public static final String OTP_HAS_BEEN_SENT_TO_THE_REGISTERED_EMAIL = "OTP has been sent to the Registered E-mail";
	public static final String GENERATED_OTP = "Generated OTP ";
	public static final String ONE_TIME_PASSWORD_IS = "One Time Password is : ";
	public static final String OTP_FOR_AUTHENTICATION = "OTP for Authentication";
	public static final String ACCEPTED = "Accepted";
	public static final String USER_LOGO_UPDATED_SUCCESSFULLY = "User Logo Updated Successfully ";
	public static final String USER_HAS_BEEN_DELETED_SUCCESSFULLY = "User Has Been Deleted Successfully";

	public static final String BILLING_USER_UPDATED_SUCCESSFULLY = "Billing User Updated Successfully";
	public static final String BILLING_USER_DELETED_SUCCESSFULLY = "Billing User Deleted Successfully";

	public static final String EMAIL_ALREADY_REGISTER = "Email or PhoneNumber Has Been Already Registered";
	// Service constant end

	// Controller Constant start
	public static final String BILLING_USER_FETCH_SUCCESSFULLY = "Billing User fetch Successfully";
	public static final String BILLING_USER_SAVE_SUCCESSFULLY = "Billing User Save Successfully";

	public static final String USER_PROFILE_UPDATED_SUCCESSFULLY = "UserProfile Updated Successfully";
	public static final String FETCH_SUCCESSFULLY = "Fetch Successfully";
	public static final String USER_SAVED_SUCCESSFULLY = "User Saved Successfully";
	public static final String ACCESS_TOKEN_GENERATED_SUCCESSFULLY = "Access Token Generated Successfully";
	public static final String USER_HAS_BEEN_BLOCKED_BY_ADMIN ="User Has Been Blocked By Admin";
	// Controller Constant end

	// Roles

	public static final String AUTHORITIES = "hasAnyAuthority('ROLE_ADMIN','ROLE_AIRPORT','ROLE_HOTEL','ROLE_AIRLINE','ROLE_EXTERNAL_HANDLING_COMPANY','ROLE_TRANSPORTATION')";

}
