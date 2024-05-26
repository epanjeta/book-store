import React, {useEffect, useState} from 'react'
import {Button, Container, Dropdown, Form, Grid, Message, Segment,} from 'semantic-ui-react'
import {Formik} from "formik";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {getAllUsers} from "../../api/users";
import {exportOrders, exportUsers} from "../../api/export";
import { format } from 'date-fns';

const Export = () =>  {
    const [users, setUsers] = useState();
    const [userOptions, setUserOptions] = useState(undefined);
    const [pdfData, setPdfData] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await getAllUsers();
                setUsers(response);
                const options = response.map(p => {
                    return {
                        key: p.id,
                        text: p.firstName + ' ' + p.lastName + ' - ' + p.userName,
                        value: p.id,
                    }
                })
                setUserOptions(options);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };

        fetchUsers()
    }, [])

    const handleSubmitOrderExport = async (values) => {
        const data = {
            dateUntil: format(values.validTo, 'dd/MM/yyyy'),
            dateFrom: format(values.validFrom, 'dd/MM/yyyy'),
            ...pdfData
        }
        console.info(data);
        try {
            exportOrders(data).then(res => {
                console.info(res)
                if (res.status === 200) {
                    const url = window.URL.createObjectURL(new Blob([res.data], {type:'application/pdf'}));
                    const link = document.createElement('a');
                    link.href = url;
                    const currDate = new Date().toLocaleDateString();
                    const currTime = new Date().toLocaleTimeString();
                    link.setAttribute('download', 'orders_'+ currDate + '_' + currTime + '.pdf')
                    document.body.appendChild(link);
                    link.click();
                    link.remove();
                }

            });
        } catch (err) {
            console.error('Unable to create order export', err);
            toast.error("Unable to create order export")
        }
    }

    const handleSubmitUserExport = async () => {
        console.info('hi')
        try {
            exportUsers().then(res => {
                console.info(res)
                if (res.status === 200) {
                    const url = window.URL.createObjectURL(new Blob([res.data], {type:'application/pdf'}));
                    const link = document.createElement('a');
                    link.href = url;
                    const currDate = new Date().toLocaleDateString();
                    const currTime = new Date().toLocaleTimeString();
                    link.setAttribute('download', 'users_'+ currDate + '_' + currTime + '.pdf')
                    document.body.appendChild(link);
                    link.click();
                    link.remove();
                }

            });
        } catch (err) {
            console.error('Unable to create order export', err);
            toast.error("Unable to create order export")
        }
    }

    const value = {
        "userId": undefined,
        "validTo": undefined,
        "validFrom": undefined
    }

    const setTheUser = (e, d) => {
        let copyBook = pdfData;
        copyBook.userId = d.value
        setPdfData(copyBook);
    }

    return (
        <Container>
            {userOptions && <Grid centered verticalAlign="middle">
                <Grid.Column style={{ maxWidth: 800 }}>
                    <Segment>
                        <h1>Create an order export</h1>
                        <Formik
                            initialValues={value}
                            onSubmit={handleSubmitOrderExport}
                        >
                            {formik => (
                                <Form onSubmit={formik.handleSubmit}>
                                    <Form.Field>
                                        <label>Date from</label>
                                        <Form.Input
                                            type="date"
                                            name="validFrom"
                                            placeholder="Date from"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.validFrom}
                                        />
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Date until</label>
                                        <Form.Input
                                            type="date"
                                            name="validTo"
                                            placeholder="Date until"
                                            onChange={formik.handleChange}
                                            onBlur={formik.handleBlur}
                                            value={formik.values.validTo}
                                        />
                                    </Form.Field>
                                    <Form.Field>
                                        <label>User</label>
                                        <Dropdown
                                            search
                                            options={userOptions}
                                            placeholder="Select user..."
                                            fluid
                                            selection
                                            onChange={setTheUser}
                                        />
                                    </Form.Field>

                                    <Button type="submit" primary fluid icon={'download'}>
                                        Export
                                    </Button>
                                </Form>
                            )}
                        </Formik>
                    </Segment>
                    <Segment>
                        <h1>Create an user export</h1>
                        <label>Export information about book buyer in the system.</label>
                        <Button type="submit" primary fluid icon={'download'} onClick={handleSubmitUserExport}>
                            Export
                        </Button>
                    </Segment>
                </Grid.Column>
            </Grid>}

        </Container>
    )
}

export default Export
