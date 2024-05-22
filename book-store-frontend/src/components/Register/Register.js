import React from 'react';
import { Form, Button, Message, Container, Grid, Segment } from 'semantic-ui-react';
import { Formik } from 'formik';
import { useNavigate } from "react-router-dom";
import * as Yup from 'yup';
import { toast } from "react-toastify"; 
import { register, createAddress } from 'api/users';
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
      .required('Required'),
    street: Yup.string()
      .required('Required'),
    zipCode: Yup.string()
      .required('Required'),
    cityName: Yup.string()
      .required('Required'),
    countryName: Yup.string()
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
        const address = {
            street: values.street,
            zipCode: values.zipCode,
            cityName: values.cityName,
            countryName: values.countryName
        }
        const addressResponse = await createAddress(address)
        const user = {
            firstName: values.firstName,
            lastName: values.lastName, 
            email: values.email,
            userName: values.userName,
            password: values.password,
            phoneNumber: values.phoneNumber,
            birthDay: values.birthDay,
            addressProjection: addressResponse
        }
        console.log(user)
        const registerResponse = await register(user)
        
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
                initialValues={{ email: '', password: '', firstName: '', lastName:'', userName:'', phoneNumber:'', birthDay:'', street:'', zipCode:'', cityName:'', countryName:'' }}
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
                    <Form.Field>
                        <label>Street Name</label>
                        <Form.Input
                        type="text"
                        name="street"
                        placeholder="Enter your street name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.street}
                        />
                        {formik.touched.street && formik.errors.street ? (
                        <Message negative>
                            {formik.errors.street}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>Zip Code</label>
                        <Form.Input
                        type="text"
                        name="zipCode"
                        placeholder="Enter zip code"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.zipCode}
                        />
                        {formik.touched.zipCode && formik.errors.zipCode ? (
                        <Message negative>
                            {formik.errors.zipCode}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>City Name</label>
                        <Form.Input
                        type="text"
                        name="cityName"
                        placeholder="Enter city name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.cityName}
                        />
                        {formik.touched.cityName && formik.errors.cityName ? (
                        <Message negative>
                            {formik.errors.cityName}
                        </Message>
                        ) : null}
                    </Form.Field>
                    <Form.Field>
                        <label>Country Name</label>
                        <Form.Input
                        type="text"
                        name="countryName"
                        placeholder="Enter country name"
                        onChange={formik.handleChange}
                        onBlur={formik.handleBlur}
                        value={formik.values.countryName}
                        />
                        {formik.touched.countryName && formik.errors.countryName ? (
                        <Message negative>
                            {formik.errors.countryName}
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
