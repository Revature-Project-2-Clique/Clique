import React from 'react'

const PostComponent = ({poster, createdAt, content}) => {
    return (
        <>
            <h4>{poster}</h4>
            <small>{new Date(createdAt).toLocaleString()}</small>
            <hr/>
            <p>{content}</p> 
        </>
    )
}

export default PostComponent