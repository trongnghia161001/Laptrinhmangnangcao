import FormPasswordField from "@/components/form/FormPasswordField";
import FormTextField from "@/components/form/FormTextField";
import { COLORS } from "@/constants";
import { validationSchema } from "@/libs/validations/user";
import { User } from "@/types/admin";
import Button from "@mui/material/Button";
import { useFormik } from "formik";
import React from "react";

interface IEditInformationForm {
  initialData: Partial<User>;
  onSubmit: (info: Partial<User>) => any;
  onClose: () => void;
}

function EditInformationForm(props: IEditInformationForm) {
  const isSubmitted = React.useRef(false);

  const formik = useFormik<any>({
    initialValues: {
      firstName: props.initialData.firstName,
      lastName: props.initialData.lastName,
      phoneNumber: props.initialData.phoneNumber,
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      const adminInfo = {
        ...props.initialData,
        firstName: values.firstName,
        lastName: values.lastName,
        phoneNumber: values.phoneNumber,
      } as Partial<User>;
      props.onSubmit(adminInfo);
    },
  });

  return (
    <form
      onSubmit={(e) => {
        isSubmitted.current = true;
        formik.handleSubmit(e);
      }}
      noValidate
    >
      <div className="flex flex-col gap-y-5">
        <div className="custom-input min-w-[550px]">
          <FormTextField
            key="firstName"
            label="Email"
            formik={formik}
            type="text"
            name="firstName"
            required
            isSubmitted={isSubmitted.current}
          />
        </div>
        <div className="custom-input">
          <FormTextField
            key="firstName"
            label="Password"
            formik={formik}
            type="text"
            name="firstName"
            required
            isSubmitted={isSubmitted.current}
          />
        </div>
        <div>
          <FormTextField
            key="firstName"
            label="Confirm Password"
            formik={formik}
            type="text"
            name="firstName"
            required
            isSubmitted={isSubmitted.current}
          />
        </div>
      </div>
      <div className="flex items-center justify-end gap-x-4 mt-9">
        <Button
          sx={{
            padding: "10px 16px 10px 16px",
            backgroundColor: COLORS.secondaryButton,
            color: COLORS.primary,
          }}
          onClick={props.onClose}
        >
          Cancel
        </Button>
        <Button
          sx={{
            padding: "10px 16px 10px 16px",
            backgroundColor: COLORS.primary,
            color: COLORS.white,
            "&:hover": {
              backgroundColor: COLORS.primary,
              opacity: 0.7,
            },
          }}
          autoFocus
          type="submit"
        >
          Confirm
        </Button>
      </div>
    </form>
  );
}

export default EditInformationForm;
