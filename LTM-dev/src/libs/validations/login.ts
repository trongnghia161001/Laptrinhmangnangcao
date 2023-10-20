import * as yup from "yup";

export const validationSchema = yup.object({
  username: yup.string().required("IDを入力してください"),
  password: yup
    .string()
    .min(4, "Min length password is 4")
    .max(16, "Max length password is 16")
    .required("Please enter password !"),
});
