import { getCartDetails, removeItemFromCart } from 'api/carts';
import React, { useEffect, useState } from 'react';
import { Table, Button, Header, Container, Divider } from 'semantic-ui-react';
import styled from 'styled-components';
import placeholder from 'images/placeholder.png';
import { useStore } from 'components/Login/StoreContext';

const RemoveButton = styled(Button)`
  &&& {
    color: orange; 
    border: 1px solid orange; 
    background-color: transparent; 

    &:hover {
      color: white; 
      border-color: white; 
      background-color: orange; 
    }
  }
`;

const InfoContainer = styled.div`
  margin-top: 20px;
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  align-self: flex-end;
  flex: 1;
  border: 1px solid #ddd; /* Set border color to light grey */
  border-radius: 10px; /* Set border radius */
  padding: 10px; /* Add padding */
`;

const GridContainer = styled.div`
  flex: 1;
`;


const Cart = () => {
    const [cartDetails, setCartDetails] = useState([]);
    const { user } = useStore();

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const params = {userId: parseInt(user.userId)}
                const response = await getCartDetails(params);
                setCartDetails(response);
            } catch (error) {
                console.error('Error fetching cart details:', error);
            }
        };
        fetchBooks();
    }, []);


    const removeFromCart = async (id, quantity) => {
        try {
            const data = {
                "userId": 11,
                "bookId": id,
                "quantity": quantity
            }
            await removeItemFromCart(data);
            const updatedCartDetails = await getCartDetails({ userId: 11 });
            setCartDetails(updatedCartDetails);
        } catch (err) {
            console.error('Unable to remove', err);
        } 
      };

    // Calculate total price of all items in the cart
    const totalPrice = cartDetails.reduce((acc, item) => acc + item.book.price, 0);

    return (
        <Container style={{ display: "flex", flexDirection: "column", marginTop: '20px', marginBottom: '20px' }}>
            <GridContainer>
                <h2>Shopping Cart</h2>
                {cartDetails && cartDetails.length === 0 && (
                    <Header color='orange'>No products found in your cart...</Header>
                )}
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Picture</Table.HeaderCell>
                            <Table.HeaderCell>Title</Table.HeaderCell>
                            <Table.HeaderCell>Quantity</Table.HeaderCell>
                            <Table.HeaderCell>Price</Table.HeaderCell>
                            <Table.HeaderCell>Action</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {cartDetails?.map((item, id) => (
                            <Table.Row key={id}>
                                <Table.Cell textAlign='center'><img src={placeholder} alt="book-pic" style={{ maxWidth: '200px', height: 'auto' }} /></Table.Cell>
                                <Table.Cell textAlign='center'>{item.book.title}</Table.Cell>
                                <Table.Cell textAlign='center'>{item.quantity}</Table.Cell>
                                <Table.Cell textAlign='center'>${item.book.price}</Table.Cell>
                                <Table.Cell textAlign='center'>
                                    <RemoveButton onClick={() => removeFromCart(item.book.id, item.quantity)}>
                                        Remove
                                    </RemoveButton>
                                </Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
                </GridContainer>
                <Divider />
                <InfoContainer>
                    <Header as='h4'>Shopping Cart Information</Header>
                    <Divider />
                    <Header as='h4'>Cart Value: {totalPrice.toFixed(2)} $</Header> 
                    <p>You can complete your order in the next step.</p>
                    <Button color='orange'>Complete Order</Button>
                </InfoContainer>
        </Container>
    );
};

export default Cart;
