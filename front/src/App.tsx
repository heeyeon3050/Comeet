import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { NavBar } from "./components/Common/NavBar";
import { RoomList } from "./pages/RoomList";
import { Mainpage } from "./pages/Mainpage";
import { Community } from "./pages/Community";
import { Mypage } from "./pages/Mypage";
import { LoginPage } from "./pages/LoginPage";
import { SignupPage } from "./pages/SignupPage";
import { Room } from "./pages/Room";
import ConditionCheck from "./pages/ConditionCheck";
import TextEditor from "./components/TextEditor";
import { RecruitBoardList } from "./pages/RecruitBoardList";
import { FreeBoardList } from "./pages/FreeBoardList";
//import { RecruitBoardDetail } from "./pages/RecruitBoardDetail";
import Board from "./pages/Board";
import { BoardDetail } from "./pages/BoardDetail";
import Login from "./components/Auth/Login";
import { UserState } from "./types";
import { UseSelector, useSelector } from "react-redux";
import ProfileEdit from "pages/ProfileEdit";
import RoomCreate from "pages/RoomCreate";
import tw from "tailwind-styled-components";

function App() {
  //임시
  const user = useSelector((state) => state);
  const [isLogin, setIsLogin] = React.useState<boolean>(false);
  const [isModal, setIsModal] = React.useState<boolean>(false);
  const modalHandler = () => {
    setIsModal(!isModal);
  };

  return (
    <div className="App h-dvh">
      <BrowserRouter>
        <NavBarContainer>
          <NavBar />
        </NavBarContainer>
        <RoutesContainer>
          <Routes>
            <Route path="/" element={<Mainpage />} />
            {!isLogin ? (
              <>
                <Route path="/room-regist" element={<RoomCreate />} />
                <Route path="/roomlist" element={<RoomList />} />

                {/* 커뮤니티인데 곧 삭제 예정... */}
                <Route path="/community" element={<Community />} />

                {/* 모집 게시판 */}
                <Route path="/recruit-board" element={<RecruitBoardList />} />

                <Route path="/recruit-board/edit" element={<Board isFree={true} isEdit={true} />} />
                {/* 모집게시판 글 상세보기 */}
                <Route path="/recruit-board/:boardId" element={<BoardDetail />}></Route>

                {/* 자유 게시판 */}
                <Route path="/free-board" element={<FreeBoardList />}></Route>

                {/* 자유게시판 글 상세보기 */}
                <Route path="/free-board/:boardId" element={<BoardDetail />}></Route>

                {/* 마이페이지 */}
                <Route path="/mypage" element={<Mypage />} />
                <Route path="/profile-edit" element={<ProfileEdit />}></Route>
                <Route path="/room/:roomId/*" element={<Room />} />
              </>
            ) : (
              <>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/signup" element={<SignupPage />} />
              </>
            )}
            <Route path="/before-entrance" element={<ConditionCheck />} />
          </Routes>
        </RoutesContainer>
      </BrowserRouter>
    </div>
  );
}

const NavBarContainer = tw.div`
fixed
w-full
h-12
z-10
`;

const RoutesContainer = tw.div`
pt-12
`;

export default App;
