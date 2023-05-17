import "bootstrap/dist/css/bootstrap.min.css"
import "./App.css"

import { Auth, Items, Exhibitions, Collections, Stories, AddItem, AddCollection,
AddExhibition, ModifyCollection, DelExhibition, DelItem, ModifyExhibition} from "./components"

import { BrowserRouter, Routes, Route } from "react-router-dom"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Auth />} />
        <Route path="/items" element={<Items />} />
        <Route path="/exhibitions" element={<Exhibitions />} />
        <Route path="/collections" element={<Collections />} />
        <Route path="/stories" element={<Stories />} />
        <Route path="/items/add" element={<AddItem />} />
        <Route path="/collections/add" element={<AddCollection />} />
        <Route path="/exhibitions/add" element={<AddExhibition />} />
        <Route path="/items/delete" element={<DelItem />} />
        
        <Route path="/exhibitions/delete" element={<DelExhibition />} />
        <Route path="/collections/modify" element={<ModifyCollection />} />
        <Route path="/exhibitions/modify" element={<ModifyExhibition />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;