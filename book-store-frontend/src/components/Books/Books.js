import React, {useState, useEffect} from 'react';
import {getBooks} from 'api/books';
import {Card, Image, Divider} from 'semantic-ui-react';
import placeholder from 'images/placeholder.png';
import styled from 'styled-components';
import {useNavigate} from 'react-router-dom';
import LoadingSpinner from "../loading/LoadingSpinner";

const Container = styled.div`
  padding: 4rem;
`;

const Header = styled.h2`
  text-align: left;
`;

const BookCard = styled(Card)`
  &&& {
    margin-bottom: 20px;
  }
`;

const Books = () => {
    const navigate = useNavigate();
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const response = await getBooks();
                setBooks(response);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };
        fetchBooks();
    }, []);

    return (
        <>
            {
                books.length > 0 ? <Container>
                    <Header>Books</Header>
                    <Divider/>
                    <Card.Group itemsPerRow={5}>
                        {books.map((book, index) => (
                            <BookCard key={index} onClick={() => navigate('/books/details/' + book.id)}>
                                <Image src={placeholder} wrapped ui={false}/>
                                <Card.Content>
                                    <Card.Header>{book.title}</Card.Header>
                                    <Card.Meta>{book.authors.map(author => `${author.firstName} ${author.lastName}`).join(', ')}</Card.Meta>
                                    <Card.Description>{book.description}</Card.Description>
                                </Card.Content>
                                <Card.Content extra>
                                    <Card.Header>{`${book.price} $`}</Card.Header>
                                </Card.Content>
                            </BookCard>
                        ))}
                    </Card.Group>
                </Container> : <LoadingSpinner/>
            }

        </>

    );
};

export default Books;
