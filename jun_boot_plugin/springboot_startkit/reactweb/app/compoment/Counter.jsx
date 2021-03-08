import React, {Component, PropTypes} from 'react'
const Counter = React.createClass({
    propTypes: {
        value: PropTypes.object,
        onIncrement:PropTypes.function,
        onDecrement:PropTypes.function,
        cleanState: PropTypes.func
    },
    getInitialState() {
        return {};
    },
    cleanState(){
        this.props.cleanState();
    },
    incrementIfOdd() {
        if (this.props.value.count % 2 !== 0) {
            this.props.onIncrement()
        }
    },

    incrementAsync() {
        setTimeout(this.props.onIncrement, 1000)
    },

    render() {
        return (
            <p>
                Clicked: {this.props.value.count} times
                {' '}
                <button onClick={this.props.onIncrement}>
                    +
                </button>
                {' '}
                <button onClick={this.props.onDecrement}>
                    -
                </button>
                {' '}
                <button onClick={this.incrementIfOdd}>
                    Increment if odd
                </button>
                {' '}
                <button onClick={this.incrementAsync}>
                    Increment async
                </button>
                <button onClick={this.cleanState}>
                    Clean
                </button>
            </p>
        )
    }
});

export default Counter