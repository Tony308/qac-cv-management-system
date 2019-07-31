import React, {Component} from 'react';
import HomePageComponent from "../components/HomePageComponent";

export default class HomePageContainer extends Component {

    listCVs(rows, data) {
        for (let x  = 0; x < data.length; x++) {
            let row = data[x];

            rows.push(
                <tr key={x}>
                    <td>{row.id}</td><td>{row.name}</td>
                    <td><button className="btn" onClick={() => {
                        this.props.deleteCV(data[x].id)
                    }}><i className="fa fa-trash"></i></button></td>
                </tr>
            );
        }
    }

    componentDidMount() {
        this.props.getCVs();
    }

    render() {
        let rows = [];
        let data = this.props.data;
        this.listCVs(rows,data);

        return(
            <HomePageComponent
                uploadCV={this.props.uploadCV}
                getCVs={this.props.getCVs}
                CVs={rows}
            />
        );
    }
}