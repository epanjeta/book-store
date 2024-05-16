import React from 'react';
import { Form, Button, Message, Container, Grid, Segment } from 'semantic-ui-react';
import { Formik } from 'formik';
import { useNavigate } from "react-router-dom";
import * as Yup from 'yup';
import { toast } from "react-toastify"; 
import { register } from 'api/users';
import { useStore } from 'components/Login/StoreContext';

const validationSchema = Yup.object({
    email: Yup.string()
      .email('Invalid email address')
      .required('Required'),
    password: Yup.string()
      .min(6, 'Password must be at least 6 characters')
      .required('Required'),
    firstName: Yup.string()
      .required('Required'),
    lastName: Yup.string()
      .required('Required'),
    userName: Yup.string()
      .required('Required')
  });

const Register = () => {
    const navigate = useNavigate();
    const { setUser } = useStore();

    const handleClick = () => {
        navigate("/login")
      }
    
    const handleSubmit = async (values) => {
      try {
        const registerResponse = await register(values)
          console.log('ovdje')
        if(registerResponse.errorMessage == null || registerResponse.errorMessage === ""){
            navigate("/");
        }
        else {
          alert(registerResponse.errorMessage);
        }
      } catch (err) {
        toast.error("Unable to register!")
      }
    };
    return (
        <Container>
        <Grid centered verticalAlign="middle" style={{ height: '100vh' }}>
            <Grid.Column style={{ maxWidth: 450 }}>
            <Segment>
                <h1>Signup</h1>
                <Formik
                initialValues={{ email: '', password: '', firstName: '', lastName:'', userName:'', phoneNumber:'', birthDay:'' }}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
                >
                {formik => (
                    <Form onSubmit={formik.handleSubmit}>
                    <Form.Field>
                        <label>First Name</label>
                        <Form.Input
                        type="text"
                        name="firstName"
                        placeholder="Enter your first name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.firstname}
                        />
                        {formik.touched.firstname && formik.errors.firstname ? (
                        <Message negative>
                            {formik.errors.firstname}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>Last Name</label>
                        <Form.Input
                        type="text"
                        name="lastName"
                        placeholder="Enter your last name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.lastname}
                        />
                        {formik.touched.lastname && formik.errors.lastname ? (
                        <Message negative>
                            {formik.errors.lastname}
                        </Message>
                        ) : null}
                    </Form.Field>
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
                    <Form.Field>
                        <label>Username</label>
                        <Form.Input
                        type="text"
                        name="userName"
                        placeholder="Enter your username"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.username}
                        />
                        {formik.touched.username && formik.errors.username ? (
                        <Message negative>
                            {formik.errors.username}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>Phone Number</label>
                        <Form.Input
                        type="text"
                        name="phoneNumber"
                        placeholder="Enter your phone number"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.phonenumber}
                        />
                        {formik.touched.phonenumber && formik.errors.phonenumber ? (
                        <Message negative>
                            {formik.errors.phonenumber}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>Birthday</label>
                        <Form.Input
                        type="date"
                        name="birthDay"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.birthday}
                        />
                        {formik.touched.birthday && formik.errors.birthday ? (
                        <Message negative>
                            {formik.errors.birthday}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Button type="submit" primary fluid disabled={formik.isSubmitting}>
                        Submit
                    </Button>
                    <Button basic color='blue' fluid onClick={handleClick} style={{ marginTop: '10px' }}>
                        Already have an account? Login here!
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

export default Register;
