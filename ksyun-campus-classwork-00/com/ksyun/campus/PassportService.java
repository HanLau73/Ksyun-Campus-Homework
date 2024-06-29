package com.ksyun.campus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PassportService {

    // 假设这是最近三次使用过的密码，实际情况您可能需要从数据库或文件中读取
    private Set<String> recentPasswords = new HashSet<>(Arrays.asList("password1", "Password2", "password3"));

    // 该方法不抛出异常，返回true则代表密码修改成功，否则修改失败。
    // 修改或检验制作的原因可以以日志形式输出
    public boolean changePswd(String account, String oldPswd, String newPswd) {
        if (newPswd == null || newPswd.length() < 10) {
            System.out.println("密码长度不足10位");
            return false;
        }

        if (newPswd.toLowerCase().contains(account.toLowerCase()) || newPswd.contains(" ")) {
            System.out.println("密码不能包含账号或空格");
            return false;
        }

        String[] forbiddenStrings = {"kingsoft", "wps", "ksc", "xsj", "wust", "wit"};
        for (String str : forbiddenStrings) {
            if (newPswd.toLowerCase().contains(str)) {
                System.out.println("密码包含不允许的字符串: " + str);
                return false;
            }
        }

        if (hasSequentialLettersOrDigits(newPswd)) {
            System.out.println("密码包含连续的数字或字母");
            return false;
        }

        if (!meetsComplexityRequirements(newPswd)) {
            System.out.println("密码复杂性不满足要求");
            return false;
        }

        if (recentPasswords.contains(newPswd)) {
            System.out.println("密码是最近三次使用过的密码");
            return false;
        }

        // 这里应当添加逻辑来更新密码历史，以便下次验证时使用
        // recentPasswords.add(newPswd);

        return true;
    }

    private boolean hasSequentialLettersOrDigits(String password) {
        for (int i = 0; i < password.length() - 1; i++) {
            char current = password.charAt(i);
            char next = password.charAt(i + 1);

            if (current + 1 == next && (Character.isDigit(current) || Character.isLetter(current))) {
                return true;
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
}
