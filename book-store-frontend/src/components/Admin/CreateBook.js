import React, {useEffect, useState} from 'react'
import {Button, Container, Dropdown, Form, Grid, Message, Segment,} from 'semantic-ui-react'
import {Formik} from "formik";
import * as Yup from "yup";
import {getPublishers} from "../../api/publisher";
import {getAuthors} from "../../api/authors";
import {getGenres, getLanguage} from "../../api/autocomplete";
import {addItemToCart} from "../../api/carts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {createBook} from "../../api/books";

const validationSchema = Yup.object({
    isbn: Yup.string()
        .required('Required'),
    title: Yup.string()
        .required('Required'),
    price: Yup.number()
        .min(0)
        .required('Required'),
    stock: Yup.number()
        .min(1)
        .required('Required'),
    publicationDate: Yup.date()
        .required('Required'),
    description: Yup.string()
        .max(255)
});

const CreateBook = () =>  {
    const [languages, setLanguages] = useState();
    const [languageOptions, setLanguageOptions] = useState();
    const [genres, setGenres] = useState([]);
    const [genreOptions, setGenreOptions] = useState([]);
    const [publishers, setPublishers] = useState([]);
    const [publisherOptions, setPublisherOptions] = useState([]);
    const [authors, setAuthors] = useState([]);
    const [authorOptions, setAuthorOptions] = useState([]);
    const [book, setBook] = useState([]);
    const navigate = useNavigate();

    const stateOptions = [
        {
            key: 'author',
            text: 'author',
            value: 'author',
        },
        {
            key: '1',
            text: '1',
            value: '1',
        }
    ]

    useEffect(() => {
        const fetchPublishers = async () => {
            try {
                const response = await getPublishers();
                setPublishers(response);
                const options = response.map(p => {
                    return {
                        key: p.id,
                        text: p.name + ' - ' + p.email,
                        value: p.id,
                    }
                })
                setPublisherOptions(options);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };
        const fetchAuthors = async () => {
            try {
                const response = await getAuthors();
                setAuthors(response);
                const options = response.map(p => {
                    return {
                        key: p.id,
                        text: p.firstName + ' ' + p.lastName,
                        value: p.id,
                    }
                })
                setAuthorOptions(options);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };

        const fetchGenres = async () => {
            try {
                const response = await getGenres();
                setGenres(response);
                const options = response.map(p => {
                    return {
                        key: p.name,
                        text: p.name,
                        value: p.name,
                    }
                })
                setGenreOptions(options);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };

        const fetchLanguages = async () => {
            try {
                const response = await getLanguage();
                setLanguages(response);
                const options = response.map(p => {
                    return {
                        key: p.code,
                        text: p.name,
                        value: p.code,
                    }
                })
                setLanguageOptions(options);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };

        fetchPublishers();
        fetchGenres();
        fetchAuthors();
        fetchLanguages();
    }, [])

    const handleSubmit = async (values) => {
        const data = {
            ...values,
            ...book
        }

        console.info(data);
        try {
            await createBook(data);
            toast.success("New book created!");
            navigate("/books");
        } catch (err) {
            console.error('Unable to create the book', err);
            toast.error("Unable to add to cart!")
        }

    };

    const setThePublisher = (e, d) => {
        let copyBook = book;
        copyBook.publisherProjection = publishers.find((element) => element.id === d.value);
    }
    const setTheLanguage = (e, d) => {
        let copyBook = book;
        copyBook.languageCode = d.value
        setBook(copyBook);
    }

    const setTheAuthors = (e, d) => {
        let copyBook = book;
        copyBook.authors = authors.filter(author => d.value.includes(author.id));
        setBook(copyBook);
    }

    const setTheGenres = (e, d) => {
        let copyBook = book;
        copyBook.genres = d.value
        setBook(copyBook);
    }

    const value = {
        "title": "",
        "languageCode": "",
        "description": "",
        "price": 0,
        "stock": 0,
        "publicationDate": "",
        "publisherProjection": "",
        "imageProjection": {
            "id": 1
        },
        "genres": [

        ],
        "authors": [

        ],
        "isbn": ""
    }

    return (
        <Container>
            <Grid centered verticalAlign="middle">
                <Grid.Column style={{ maxWidth: 800 }}>
                    <Segment>
                        <h1>Create a book</h1>
                        <Formik
                            initialValues={value}
                            onSubmit={handleSubmit}
                            validationSchema={validationSchema}
                        >
                            {formik => (
                                <Form onSubmit={formik.handleSubmit}>
                                    <Form.Field>
                                        <label>ISBN</label>
                                        <Form.Input
                                            type="text"
                                            name="isbn"
                                            placeholder="ISBN"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.isbn}
                                        />
                                        {formik.touched.isbn && formik.errors.isbn ? (
                                            <Message negative>
                                                {formik.errors.isbn}
                                            </Message>
                                        ) : null}
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Title</label>
                                        <Form.Input
                                            type="text"
                                            name="title"
                                            placeholder="Title"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.title}
                                        />
                                        {formik.touched.title && formik.errors.title ? (
                                            <Message negative>
                                                {formik.errors.title}
                                            </Message>
                                        ) : null}
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Publication date</label>
                                        <Form.Input
                                            type="date"
                                            name="publicationDate"
                                            placeholder="Publication date"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.publicationDate}
                                        />
                                        {formik.touched.publicationDate && formik.errors.publicationDate ? (
                                            <Message negative>
                                                {formik.errors.publicationDate}
                                            </Message>
                                        ) : null}
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Language</label>
                                        <Dropdown
                                            search
                                            options={languageOptions}
                                            placeholder="Select authors..."
                                            fluid
                                            selection
                                            onChange={setTheLanguage}
                                        />
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Price</label>
                                        <Form.Input
                                            type="number"
                                            name="price"
                                            placeholder="0"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.price}
                                        />
                                        {formik.touched.price && formik.errors.price ? (
                                            <Message negative>
                                                {formik.errors.price}
                                            </Message>
                                        ) : null}
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Stock</label>
                                        <Form.Input
                                            type="number"
                                            name="stock"
                                            placeholder="0"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.stock}
                                        />
                                        {formik.touched.stock && formik.errors.stock ? (
                                            <Message negative>
                                                {formik.errors.stock}
                                            </Message>
                                        ) : null}
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Description</label>
                                        <Form.TextArea
                                            name="description"
                                            placeholder="Add the book description..."
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.description}
                                        />
                                        {formik.touched.description && formik.errors.description ? (
                                            <Message negative>
                                                {formik.errors.description}
                                            </Message>
                                        ) : null}
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Publisher</label>
                                        <Dropdown
                                            search
                                            options={publisherOptions}
                                            placeholder="Select publishers..."
                                            fluid
                                            selection
                                            onChange={setThePublisher}
                                        />
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Genres</label>
                                        <Dropdown
                                            search
                                            options={genreOptions}
                                            multiple={true}
                                            placeholder="Select authors..."
                                            fluid
                                            selection
                                            onChange={setTheGenres}
                                        />
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Authors</label>
                                        <Dropdown
                                            search
                                            options={authorOptions}
                                            multiple
                                            placeholder="Select authors..."
                                            fluid
                                            selection
                                            onChange={setTheAuthors}
                                        />
                                    </Form.Field>


                                    <Button type="submit" primary fluid>
                                        Create
                                    </Button>
                                </Form>
                            )}
                        </Formik>
                    </Segment>
                </Grid.Column>
            </Grid>
        </Container>
    )
}

export default CreateBook
