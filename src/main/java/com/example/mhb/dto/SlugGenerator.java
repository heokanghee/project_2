package com.example.mhb.dto;

public class SlugGenerator {

    public static String generateSlug(String title) {
        if (title == null || title.isEmpty()) {
            return "untitled"; // 입력 문자열이 없을 경우 기본값 반환
        }

        // 스페이스로 스플릿하여 배열에 담기
        String[] words = title.split("\\s+");

        // "-" (하이픈)으로 연결
        String slug = String.join("-", words);

     // 특수 문자 및 공백 제거 (한글 포함)
        slug = slug.replaceAll("[^\\p{Alnum}-가-힣]+", "");

        // 시작 및 끝에 있는 하이픈 제거
        slug = slug.replaceAll("^-|-$", "");

        return slug;
    }

    public static void main(String[] args) {
        String title = "슬러그가 잘 만들어 지나요?! 17 hello!@@@";
        String slug = SlugGenerator.generateSlug(title);
        System.out.println(slug);  // 출력: "슬러거-슬르그-씨레기가"
    }
}