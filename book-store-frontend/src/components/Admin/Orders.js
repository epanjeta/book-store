import React, {useEffect, useState} from 'react';
import {Table, Button, Header, Container, Divider, Dropdown, DropdownMenu, DropdownItem} from 'semantic-ui-react';
import styled from 'styled-components';
import {useStore} from 'components/Login/StoreContext';
import {getOrders, updateOrders} from "../../api/orders";


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


const Orders = () => {
    const [orders, setOrders] = useState([]);
    const {user} = useStore();

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const params = {userId: parseInt(user.userId)}
                const response = await getOrders(params);
                setOrders(response);
            } catch (error) {
                console.error('Error fetching order details:', error);
            }
        };
        fetchOrders();
    }, []);

    const updateOrder = async (item, status) => {
        const data = {
            ...item,
            status: status
        }
        await updateOrders(data);
        const fetchOrders = async () => {
            try {
                const params = {userId: parseInt(user.userId)}
                const response = await getOrders(params);
                setOrders(response);
            } catch (error) {
                console.error('Error fetching order details:', error);
            }
        };
        fetchOrders();
    }

    return (
        <Container style={{display: "flex", flexDirection: "column", marginTop: '20px', marginBottom: '20px'}}>
            <GridContainer>
                <h2>Orders</h2>
                {orders && orders.length === 0 && (
                    <Header color='orange'>No orders...</Header>
                )}
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Ordered at:</Table.HeaderCell>
                            <Table.HeaderCell>Total price:</Table.HeaderCell>
                            <Table.HeaderCell>Payment method:</Table.HeaderCell>
                            <Table.HeaderCell>Status: </Table.HeaderCell>
                            { user?.role === 'ADMINISTRATOR' && <Table.HeaderCell>Action: </Table.HeaderCell>
                            }
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {orders?.map((item, id) => (
                            <Table.Row key={id}>
                                <Table.Cell textAlign='center'>{item.orderDate}</Table.Cell>
                                <Table.Cell textAlign='center'>{item.total}</Table.Cell>
                                <Table.Cell textAlign='center'>{item.paymentMethod}</Table.Cell>
                                <Table.Cell textAlign='center'>{item.status}</Table.Cell>
                                { user?.role === 'ADMINISTRATOR' &&
                                    <>
                                        {
                                            item.status === 'COMPLETED' && <Table.Cell textAlign='center'></Table.Cell>
                                        }
                                        {
                                            item.status === 'CANCELLED' && <Table.Cell textAlign='center'></Table.Cell>
                                        }
                                        {
                                            item.status === 'SHIPPED' && <Table.Cell textAlign='center'>
                                                <Dropdown text='Choose an action'>
                                                    <DropdownMenu>
                                                        <DropdownItem text='COMPLETED...'onClick={() => updateOrder(item, 'COMPLETED')}/>
                                                        <DropdownItem text='CANCELLED'  onClick={() => updateOrder(item, 'CANCELLED')} />
                                                    </DropdownMenu>
                                                </Dropdown>
                                            </Table.Cell>
                                        }
                                        {
                                            item.status === 'PROCESSING' && <Table.Cell textAlign='center'>
                                                <Dropdown text='Choose an action'>
                                                    <DropdownMenu>
                                                        <DropdownItem text='SHIPPED' onClick={() => updateOrder(item, 'SHIPPED')}/>
                                                        <DropdownItem text='COMPLETED...'onClick={() => updateOrder(item, 'COMPLETED')}/>
                                                        <DropdownItem text='CANCELLED'  onClick={() => updateOrder(item, 'CANCELLED')} />
                                                    </DropdownMenu>
                                                </Dropdown>
                                            </Table.Cell>
                                        }
                                        {
                                            item.status === 'PENDING' && <Table.Cell textAlign='center'>
                                            <Dropdown text='Choose an action'>
                                                <DropdownMenu>
                                                    <DropdownItem text='SHIPPED' onClick={() => updateOrder(item, 'SHIPPED')}/>
                                                    <DropdownItem text='COMPLETED...'onClick={() => updateOrder(item, 'COMPLETED')}/>
                                                    <DropdownItem text='CANCELLED'  onClick={() => updateOrder(item, 'CANCELLED')} />
                                                    <DropdownItem text='PROCESSING'  onClick={() => updateOrder(item, 'PROCESSING')} />
                                                </DropdownMenu>
                                            </Dropdown>
                                            </Table.Cell>
                                        }
                                    </>
                                }
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
            </GridContainer>
            <Divider/>
            <InfoContainer>

            </InfoContainer>
        </Container>
    );
};

export default Orders;
