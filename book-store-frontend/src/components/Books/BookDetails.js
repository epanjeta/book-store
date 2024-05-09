import React, { useState }from 'react';
import { useLocation } from 'react-router-dom';
import { Button, Image } from 'semantic-ui-react';
import styled from 'styled-components';
import placeholder from 'images/placeholder.png';
import NumberInput from 'semantic-ui-react-numberinput';
import { addItemToCart } from 'api/carts';
import { useStore } from 'components/Login/StoreContext';
import { toast } from "react-toastify"; 
import { useNavigate } from "react-router-dom";

const StyledContainer = styled.div`
  display: flex;
  padding: 3rem;
`;

const TextContainer = styled.div`
  display: flex;
  padding: 0.5rem;
`;

const TextBreak = styled.div`
  display: flex;
  height: 1rem;
`;

const StyledContainerLeft = styled.div`
  display: flex;
  flex: 1;
`;

const StyledContainerRight = styled.div`
  display: flex;
  flex: 3;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  padding-left: 2rem;
`;

const SpanCustomWeight = styled.span`
  font-weight: ${props => props.weight || 'normal'};
`;

const FlexContainer = styled.div`
  display: flex;
`;

const DescriptionContainer = styled.div`
  display: flex;
  padding-left: 3rem;
  padding-right: 3rem;
  flex-direction: column;
`;


const WrapperDiv = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-content: center;
`;

const BookDetails = () => {
  const location = useLocation();
  const [book, setBook] = useState(location.state);
  const [quantity, setQuantity] = useState('1');
  const { user } = useStore();
  const navigate = useNavigate();

  const addToCart = async () => {
    try {
        const data = {
          "userId": parseInt(user.userId),
          "bookId": book.id,
          "quantity": parseInt(quantity)
        }
        console.log(typeof(data.quantity));
        await addItemToCart(data);
        toast.success("Added to cart!");
        navigate("/books");
    } catch (err) {
        console.error('Unable to add to cart', err);
        toast.error("Unable to add to cart!")
    } 
  };

  const changeValue = (newValue) => {
    setQuantity(newValue);
}
  
    return (
        <WrapperDiv>
          <StyledContainer>
            <StyledContainerLeft>
                <Image src={placeholder} />
              </StyledContainerLeft>
              <StyledContainerRight>
                    <TextContainer><h2>{book.title}</h2> </TextContainer>
                    <TextContainer><h3><SpanCustomWeight weight="700">Authors: </SpanCustomWeight>{book.authors.map(author => `${author.firstName} ${author.lastName}`).join(', ')}</h3></TextContainer>

                    <TextBreak></TextBreak>
                    <TextContainer><h4><SpanCustomWeight weight="700">Genres: </SpanCustomWeight>{book.genres.join(", ")}</h4></TextContainer>
                    <TextContainer><h4><SpanCustomWeight weight="700">In Stock: </SpanCustomWeight>{book.stock}</h4></TextContainer>
                    <TextContainer><h4><SpanCustomWeight weight="700">Price: </SpanCustomWeight>{`${book.price} $`}</h4></TextContainer>
                    <TextContainer><h4><SpanCustomWeight weight="700">Language: </SpanCustomWeight>{book.languageCode}</h4></TextContainer>
                    <FlexContainer>
                      <TextContainer><h5>Quantity:</h5></TextContainer>
                      <NumberInput size='mini' minValue={1} maxValue={book.stock} value={quantity} onChange={changeValue} />
                      <Button 
                        size = 'mini'
                        color='orange' 
                        style={{ marginLeft: '1rem' }}
                        onClick={addToCart}
                      >Add to cart</Button>
                    </FlexContainer>
              </StyledContainerRight>
          </StyledContainer>
          <DescriptionContainer>
            <h3><SpanCustomWeight weight="700">Description: </SpanCustomWeight></h3>
            <p>{book.description}</p>
          </DescriptionContainer>
        </WrapperDiv>
      );
};

export default BookDetails;