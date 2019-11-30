import React, {Component} from 'react';
import HomePageComponent from "../components/HomePageComponent";
import DisplayCVContainer from "../containers/DisplayCVContainer";
import {Redirect} from "react-router"

export default class HomePageContainer extends Component {

    listCVs(rows, data) {
        for (let x  = 0; x < data.length; x++) {
            let row = data[x];

            rows.push(
                <tr key={x}>
                    <td onClick={() => {
                        this.props.retrieveCV(row.id);

                    }}>{row.name}</td>

                    <td onClick={() => {
                      this.props.retrieveCV(row.id)
                    }}>
                      {row.fileName}</td>

                    <td>
                        <button className="btn" onClick={() => {
                            this.props.deleteCV(row.id)
                        }}><i className="fa fa-trash"/></button>
                    </td>
                    <td> <button onClick={() => {
                        this.props.updateCV(row.id)
                    }}><i className='fas fa-wrench'/></button></td>
                    <td>
                    </td>
                </tr>
            );
        }
    }

    componentDidMount() {
        this.props.getCVs();
    }

    render() {
      if (this.props.cv !== '') {
          return (
            <Redirect to="/cv"/>
          )
      }
        let rows = [];
        let data = this.props.data;
        this.listCVs(rows,data);

        return(
            <HomePageComponent
                uploadCV={this.props.uploadCV}
                getCVs={this.props.getCVs}
                CVs={rows}
                updateCV={this.props.updateCV}

            />
        );
    }
}
