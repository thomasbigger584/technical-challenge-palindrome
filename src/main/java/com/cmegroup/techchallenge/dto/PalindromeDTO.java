package com.cmegroup.techchallenge.dto;

public class PalindromeDTO {
    private String value;
    private boolean palindrome;

    public PalindromeDTO() {
    }

    public PalindromeDTO(String key, boolean palindrome) {
        this.value = key;
        this.palindrome = palindrome;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isPalindrome() {
        return palindrome;
    }

    public void setPalindrome(boolean palindrome) {
        this.palindrome = palindrome;
    }

    @Override
    public String toString() {
        return "PalindromeDTO{" +
                "value='" + value + '\'' +
                ", palindrome=" + palindrome +
                '}';
    }
}
