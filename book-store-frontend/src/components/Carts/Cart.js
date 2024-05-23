import { emptyCart, getCartDetails, removeItemFromCart } from "api/carts";
import { getUser } from "api/users";
import { completeOrder } from "api/orders";
import React, { useEffect, useState } from "react";
import { Table, Button, Header, Container, Divider, Dropdown } from "semantic-ui-react";
import styled from "styled-components";
import placeholder from "images/placeholder.png";
import { useStore } from "components/Login/StoreContext";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

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
  const [userDetails, setUserDetails] = useState([]);
  const { user } = useStore();
  const [paymentMethod, setPaymentMethod] = useState([]);
  const navigate = useNavigate();
  
  let payment = [
    {key: "PAY_PAL", value: "PAY_PAL", text: "PayPal"},
    {key: "CASH_ON_DELIVERY", value: "CASH_ON_DELIVERY", text: "Cash on delivery"},
    {key: "CREDIT_CARD", value: "CREDIT_CARD", text: "Credit Card"}
]

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const params = { userId: parseInt(user.userId) };
        const response = await getCartDetails(params);
        setCartDetails(response);
      } catch (error) {
        console.error("Error fetching cart details:", error);
      }
    };
    fetchBooks();

    const fetchUser = async () => {
      try {
        const response = await getUser(parseInt(user.userId));
        setUserDetails(response);
      } catch (error) {
        console.error("Error fetching cart details:", error);
      }
    };
    fetchUser();
  }, []);

  const removeFromCart = async (id, quantity) => {
    try {
      const data = {
        userId: user.userId,
        bookId: id,
        quantity: quantity,
      };
      await removeItemFromCart(data);
      const updatedCartDetails = await getCartDetails({
        userId: parseInt(user.userId),
      });
      setCartDetails(updatedCartDetails);
    } catch (err) {
      console.error("Unable to remove", err);
    }
  };

  const createOrder = async () => {
    const bookIds = cartDetails.map((cartItem) => cartItem.book.id);
    const bookQuantities = cartDetails.map((cartItem) => cartItem.quantity);

    const orderBooks = {};
    for (let i = 0; i < bookIds.length; i++) {
      orderBooks[bookIds[i]] = bookQuantities[i];
    }

    const data = {
      userId: user.userId,
      createdAt: new Date().toISOString(),
      orderAt: new Date().toISOString(),
      total: cartDetails.reduce((acc, item) => acc + item.book.price, 0),
      status: "PENDING",
      paymentMethod: paymentMethod,
      shippingAddressId: userDetails.addressProjection.addressId,
      bookQuantity: orderBooks,
    };
    console.log(data)
    try {
      await completeOrder(data);

      navigate("/books");
      toast.success("Order completed");
    } catch (error) {
      console.error("Error fetching cart details:", error);
      toast.error("Unable to complete the order");
    }
  };

  const setThePaymentMethod = (e, { value }) => {
    setPaymentMethod(value);
  };

  // Calculate total price of all items in the cart
  const totalPrice = cartDetails.reduce(
    (acc, item) => acc + item.book.price,
    0
  );
  return (
    <Container
      style={{
        display: "flex",
        flexDirection: "column",
        marginTop: "20px",
        marginBottom: "20px",
      }}
    >
      <GridContainer>
        <h2>Shopping Cart</h2>
        {cartDetails && cartDetails.length === 0 && (
          <Header color="orange">No products found in your cart...</Header>
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
                <Table.Cell textAlign="center">
                  <img
                    src={placeholder}
                    alt="book-pic"
                    style={{ maxWidth: "200px", height: "auto" }}
                  />
                </Table.Cell>
                <Table.Cell textAlign="center">{item.book.title}</Table.Cell>
                <Table.Cell textAlign="center">{item.quantity}</Table.Cell>
                <Table.Cell textAlign="center">${item.book.price}</Table.Cell>
                <Table.Cell textAlign="center">
                  <RemoveButton
                    onClick={() => removeFromCart(item.book.id, item.quantity)}
                  >
                    Remove
                  </RemoveButton>
                </Table.Cell>
              </Table.Row>
            ))}
          </Table.Body>
        </Table>
      </GridContainer>
      <Divider />
      {cartDetails && cartDetails.length > 0 && (
        <InfoContainer>
          <Header as="h4">Shopping Cart Information</Header>
          <Divider />
          <Header as="h4">Cart Value: {totalPrice.toFixed(2)} $</Header>
          <p>You can complete your order in the next step.</p>
          <Dropdown
            search
            options={payment}
            placeholder="Select payment method"
            fluid
            selection
            onChange={setThePaymentMethod}
          />
          <Button color="orange" onClick={() => createOrder()}>
            Complete Order
          </Button>
        </InfoContainer>
      )}
    </Container>
  );
};
export default Cart;
