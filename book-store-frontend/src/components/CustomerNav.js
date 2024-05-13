import React, { useState } from 'react';
import { Menu, Icon, Dropdown, DropdownMenu, DropdownItem } from 'semantic-ui-react';
import { useNavigate } from "react-router-dom";

import { useStore } from 'components/Login/StoreContext';

const CustomerNav = () => {
    const [state, setState] = useState({ activeItem: "" });
    const navigate = useNavigate();
    const { setUser } = useStore();

    const handleItemClick = (e, { name }) => {
        setState({ activeItem: name });
        navigate(name);
    };

    const handleLogout = async (e, { name }) => {
        document.cookie = 'Bearer=; max-age=3600; path=/';
        setUser(null);
        navigate("/login");
    };

    return (
        <Menu pointing secondary>
            <Menu.Item name='books' onClick={handleItemClick} style={{ fontSize: '1.5em' }}>
                <span style={{ fontWeight: 'bold' }}>BookShop</span>
            </Menu.Item>
            <Menu.Menu position='right'>
                <Menu.Item name='cart' onClick={handleItemClick}>
                    <Icon name='shopping cart' size='large' color='grey' />
                </Menu.Item>
                <Menu.Item>
                <Dropdown
                    icon={<Icon name='user' size='large' color='grey' />}
                >
                        <DropdownMenu>
                        <DropdownItem text='Logout' onClick={handleLogout} />
                        </DropdownMenu>
                    </Dropdown>
                </Menu.Item>
            </Menu.Menu>
        </Menu>
    );
};

export default CustomerNav;