import React from 'react';
import { Form, Button, Message, Container, Grid, Segment } from 'semantic-ui-react';
import { Formik } from 'formik';
import { useNavigate } from "react-router-dom";
import * as Yup from 'yup';
import { toast } from "react-toastify"; 
import { login } from 'api/users';
import { useStore } from 'components/Login/StoreContext';

const validationSchema = Yup.object({
    email: Yup.string()
      .email('Invalid email address')
      .required('Required'),
    password: Yup.string()
      .min(6, 'Password must be at least 6 characters')
      .required('Required'),
  });

const Login = () => {
    const navigate = useNavigate();
    const { setUser } = useStore();
    
    const handleSubmit = async (values) => {
      try {
        const loginResponse = await login(values)
        if(loginResponse.errorMessage == null || loginResponse.errorMessage === ""){
          localStorage.setItem("Bearer", loginResponse.jwt);
          if (loginResponse.jwt && loginResponse.jwt !== "" && loginResponse.jwt !== undefined) {
            setUser(loginResponse);
            toast.success("Logged in!");
            navigate("/books");
          }
        }
        else{
          alert(loginResponse.errorMessage);
        }
      } catch (err) {
        toast.error("Unable to login!")
      }
    };
    return (
        <Container>
        <Grid centered verticalAlign="middle" style={{ height: '100vh' }}>
            <Grid.Column style={{ maxWidth: 450 }}>
            <Segment>
                <h1>Login</h1>
                <Formik
                initialValues={{ email: '', password: '' }}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
                >
                {formik => (
                    <Form onSubmit={formik.handleSubmit}>
                    <Form.Field>
                        <label>Email</label>
                        <Form.Input
                        type="email"
                        name="email"
                        placeholder="Enter your email"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.email}
                        />
                        {formik.touched.email && formik.errors.email ? (
                        <Message negative>
                            {formik.errors.email}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>Password</label>
                        <Form.Input
                        type="password"
                        name="password"
                        placeholder="Enter your password"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.password}
                        />
                        {formik.touched.password && formik.errors.password ? (
                        <Message negative>
                            {formik.errors.password}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Button type="submit" primary fluid disabled={formik.isSubmitting}>
                        Submit
                    </Button>
                    </Form>
                )}
                </Formik>
            </Segment>
            </Grid.Column>
        </Grid>
        </Container>
    );
};

export default Login;
