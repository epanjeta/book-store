import React, { useState } from 'react';
import { Menu, Icon } from 'semantic-ui-react';
import { useNavigate } from "react-router-dom";

const AdminNav = () => {
    const [state, setState] = useState({ activeItem: "" });
    const navigate = useNavigate();

    const handleItemClick = (e, { name }) => {
        setState({ activeItem: name });
        navigate(name);
    };
    return (
        <Menu pointing secondary>
            <Menu.Item name='books' onClick={handleItemClick} style={{ fontSize: '1.5em' }}>
                <span style={{ fontWeight: 'bold' }}>BookShop</span>
            </Menu.Item>
            <Menu.Menu position='right'>
                <Menu.Item>
                    <Icon name='cart' size='big' />
                </Menu.Item>
            </Menu.Menu>
        </Menu>
    );
};

export default AdminNav;