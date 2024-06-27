package com.ksyun.campus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PassportService {

    // 假设recentPasswords是一个LinkedList，方便移除最旧的密码和添加新密码
    private List<String> recentPasswords = new LinkedList<>(Arrays.asList("password1", "Password2", "password3"));

    public boolean changePswd(String account, String oldPswd, String newPswd) {
        if (newPswd == null || newPswd.length() < 10) {
//            System.out.println("密码长度不足10位");
            return false;
        }

        if (newPswd.toLowerCase().contains(account.toLowerCase()) || newPswd.contains(" ")) {
//            System.out.println("密码不能包含账号或空格");
            return false;
        }

        String[] forbiddenStrings = {"kingsoft", "wps", "ksc", "xsj", "wust", "wit"};
        for (String str : forbiddenStrings) {
            if (newPswd.toLowerCase().contains(str)) {
//                System.out.println("密码包含不允许的字符串: " + str);
                return false;
            }
        }

        if (hasSequentialLettersOrDigits(newPswd)) {
//            System.out.println("密码包含连续的数字或字母");
            return false;
        }

        if (!meetsComplexityRequirements(newPswd)) {
//            System.out.println("密码复杂性不满足要求");
            return false;
        }

        if (recentPasswords.stream().anyMatch(p -> p.equalsIgnoreCase(newPswd))) {
//            System.out.println("密码是最近三次使用过的密码");
            return false;
        }

        // 更新密码历史
        updateRecentPasswords(newPswd);
        return true;
    }

    private boolean hasSequentialLettersOrDigits(String password) {
        for (int i = 0; i < password.length() - 1; i++) {
            char current = password.charAt(i);
            char next = password.charAt(i + 1);

            // 使用Math.abs来判断连续性，这样可以无视字符的顺序（递增或递减）
            // 优点1: 支持递增和递减的连续性检查，更全面地识别连续字符
            if (Character.isDigit(current) && Character.isDigit(next)) {
                if (Math.abs(current - next) == 1) {
                    return true;
                }
            } else if (Character.isLetter(current) && Character.isLetter(next)) {
                // 统一转换为小写后再比较，有效处理了大小写字母的连续性
                // 优点2: 大小写不敏感，可以正确处理如'A'到'b'这样大小写混合的连续字母序列
                if (Math.abs(Character.toLowerCase(current) - Character.toLowerCase(next)) == 1) {
                    return true;
                }
            }
        }
        return false;
    }




    private boolean meetsComplexityRequirements(String password) {
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }
        int count = 0;
        if (hasUpper) {
            count++;
        }
        if (hasLower) {
            count++;
        }
        if (hasDigit) {
            count++;
        }
        if (hasSpecial) {
            count++;
        }

        return count >= 3;
    }

    private void updateRecentPasswords(String newPswd) {
        if (recentPasswords.size() >= 3) {
            recentPasswords.remove(0); // 移除最旧的密码
        }
        recentPasswords.add(newPswd); // 添加新密码
    }

//    public static void main(String[] args) {
//        PassportService passportService = new PassportService();
//        // 包含账号
//        boolean a = passportService.changePswd("hantou", "u2MDiRT7zov0pRZ", "Absan0703582");
//        System.out.println(a);
//        boolean b = passportService.changePswd("hantou", "Absan0703582", "A7san0703582");
//        System.out.println(b);
//        boolean c = passportService.changePswd("hantou", "u2MDsRT7zov0pRZ", "Acsan0703582");
//        System.out.println(c);
//        boolean d = passportService.changePswd("hantou", "Acsan0703582", "A8san0703582");
//        System.out.println(d);
//        boolean e = passportService.changePswd("hantou", "Acsan0703582", "A7san0703582");
//        System.out.println(e);
//        boolean f = passportService.changePswd("hantou", "Acsan0703582", "u2MDiRT7zov0pRZ");
//        System.out.println(f);
//        boolean g = passportService.changePswd("hantou", "Acsan0703582", "A7san0703582");
//        System.out.println(g);
////        PassportService passportService2 = new PassportService();
////        // 测试字符串"Jasan0703582"是否被识别为包含连续的数字或字母
////        String testPassword = "Jasan0703582";
////        boolean containsSequential = passportService2.hasSequentialLettersOrDigits(testPassword);
////        System.out.println("Does '" + testPassword + "' contain sequential letters or digits? " + containsSequential);
//        // 正常
//
//    }
}


